import os.path
import sys
from sys import argv
import logging

from pathlib import Path
from pyspark.sql import SparkSession
from boto3.session import Session
from pyspark.sql.functions import *
from pyspark.sql.types import *

import os
os.environ['PYSPARK_SUBMIT_ARGS'] = '--packages org.postgresql:postgresql:42.1.1 pyspark-shell'

logging.basicConfig(
     level=logging.INFO,
     format= '%(asctime)s: ORDER_DIM_INGESTION %(message)s'
 )
logger = logging.getLogger("")

def setError(msg):
    logger.error(msg)
    sys.exit(1)

def setInfo(msg):
    logger.info(msg)
    
if __name__ == '__main__':
    spark = SparkSession \
    .builder \
    .appName('order_dim_ingestion') \
    .master('local[8]') \
    .getOrCreate()
            
    if len(argv[1:]) < 2:
        setInfo('--> Enter your AWS autentication: ')
    else:
        
        #SET ARGs
        access_key = argv[1]
        secret_key = argv[2]
        
        sc = spark.sparkContext
        spark._jsc.hadoopConfiguration().set("fs.s3a.access.key", access_key)
        spark._jsc.hadoopConfiguration().set("fs.s3a.secret.key", secret_key)
        spark._jsc.hadoopConfiguration().set("fs.s3a.impl","org.apache.hadoop.fs.s3a.S3AFileSystem")

        url = "jdbc:postgresql://db/analyticsdb"
        properties = {
            "driver": "org.postgresql.Driver",
            "user": "dataeng",
            "password": "12345678"
        }


#----------------------------------------------------------
#INSERT INTO
#----------------------------------------------------------
        def insertIntoTable(df,save_mode,url,table,properties):
            df.write.mode(save_mode).jdbc(url=url,table=table,properties=properties)

#----------------------------------------------------------
#SELECT SCHEMA
#----------------------------------------------------------
        def selectSchema(url,table,properties):
            return spark.read.jdbc(url=url,table=table,properties=properties).schema

#----------------------------------------------------------
#ORDER
#----------------------------------------------------------
        df_order = spark.read.json("s3a://ifood-data-architect-test-source/order.json.gz")

        df_dim_fields = df_order.filter(df_order.customer_id.isNotNull()) \
            .select(df_order.order_id, \
                    df_order.order_scheduled.alias('scheduled'), \
                    df_order.customer_id, \
                    df_order.delivery_address_city, \
                    df_order.delivery_address_state, \
                    df_order.delivery_address_country, \
                    df_order.delivery_address_district, \
                    df_order.delivery_address_zip_code.cast('int'), \
                    df_order.merchant_id, \
                    df_order.order_created_at.cast('timestamp').alias('created_at'), \
                    df_order.order_total_amount.alias('total_amount'))

        df_order_curated = df_dim_fields.na.fill({'total_amount': 0})

        insertIntoTable(df_order_curated,'append',url,'tbl_dim_order',properties)

#----------------------------------------------------------
#ORDER ITEMS
#----------------------------------------------------------

        lst_items = df_order.select("items","order_id").collect()

        def parse_dataframe(json_data,id_data):
            r = json_data
            mylist = []
            for line in r.splitlines():
                mylist.append(line)
            rdd = sc.parallelize(mylist)
            df = spark.read.json(rdd).withColumn("id_order",lit(id_data))
            return df

        for str_item in lst_items:
            df_items = parse_dataframe(str_item[0],str_item[1])
            df_items_curated = df_items.select(df_items.externalId,df_items.name,df_items.quantity.cast('int'),\
                                               df_items.unitPrice.value.cast('decimal').alias('unitPrice'),\
                                               df_items.sequence.cast('int'),df_items.garnishItems.cast('string'),\
                                               df_items.addition.value.cast('decimal').alias('addition'),
                                               df_items.totalAddition.value.cast('decimal').alias('totalAddition'),\
                                               df_items.discount.value.cast('decimal').alias('discount'),\
                                               df_items.totalDiscount.value.cast('decimal').alias('totalDiscount'),df_items.id_order)\
            .withColumnRenamed('externalId','id')

        insertIntoTable(df_items_curated,'append',url,'tbl_dim_order_items',properties)

setInfo('--> Executado com sucesso')
sys.exit(0)  




