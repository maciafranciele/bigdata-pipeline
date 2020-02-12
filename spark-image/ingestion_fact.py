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
     format= '%(asctime)s: FACT_INGESTION %(message)s'
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
            .appName('fact_ingestion') \
            .master('local[6]') \
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
#SELECT TABLE
#----------------------------------------------------------
        def selectTable(url,table,properties):
            return spark.read.jdbc(url=url,\
                                   table=table, \
                                   properties=properties)

#----------------------------------------------------------
#INSERT INTO
#----------------------------------------------------------
        def insertIntoPartitionTable(df,save_mode,url,table,properties,):
            df.write.mode(save_mode)\
                .jdbc(url=url,
                      table=table,
                      properties=properties)

#----------------------------------------------------------
#DIM's
#----------------------------------------------------------

        df_dim_merchant = selectTable(url,'tbl_dim_merchant',properties)\
            .withColumnRenamed('id','merchant_id').persist()

        df_dim_order = selectTable(url,'tbl_dim_order',properties)\
            .withColumnRenamed('id','order_id')\
            .withColumnRenamed('merchant_id','order_merchant_id').persist()

        df_dim_items = selectTable(url,'tbl_dim_order_items',properties).persist()

        df_dim_customer = selectTable(url,'tbl_dim_order',properties).persist()

        df_order_join_items = df_dim_order.join(df_dim_items,df_dim_order.order_id == df_dim_items.id_order,'left')

        df_merchant_join_order = df_dim_merchant.join(df_order_join_items,df_dim_merchant.merchant_id == df_order_join_items.order_merchant_id,'left')\
            .withColumn('date',to_date(df_order_join_items.created_at, "yyyyMMdd"))

#----------------------------------------------------------
#FACT_MERCHANT
#----------------------------------------------------------

        df_merchant_metrics = df_merchant_join_order.groupby('merchant_id','date',).agg(
            countDistinct('order_id').alias('order_qty'),
            sum(nanvl('total_amount',lit(0))).alias('order_amount'),
            sum(nanvl('quantity',lit(0))).alias('item_qty'),
            sum(nanvl('unitprice',lit(0))).alias('item_value'),
            sum(nanvl('totaldiscount',lit(0))).alias('order_discount'))

        insertIntoPartitionTable(df_merchant_metrics.filter(df_merchant_metrics.date.isNotNull()),'append',url,'tbl_fact_merchant',properties)

#----------------------------------------------------------
#FACT_ORDER
#----------------------------------------------------------

        df_order_metrics = df_merchant_join_order.groupby('customer_id','merchant_id','date').agg(
            countDistinct('order_id').alias('order_qty'),
            sum(nanvl('total_amount',lit(0))).alias('order_amount'),
            sum(nanvl('quantity',lit(0))).alias('item_qty'),
            sum(nanvl('unitprice',lit(0))).alias('item_value'),
            sum(nanvl('totaldiscount',lit(0))).alias('order_discount'))

        insertIntoPartitionTable(df_order_metrics.filter(df_order_metrics.date.isNotNull()),'append',url,'tbl_fact_order',properties)

#----------------------------------------------------------
#FACT_CLIENT
#----------------------------------------------------------

        df_client_metrics = df_merchant_join_order.groupby('customer_id','date').agg(
            countDistinct('order_id').alias('order_qty'),
            sum(nanvl('total_amount',lit(0))).alias('order_amount'),
            sum(nanvl('quantity',lit(0))).alias('item_qty'),
            sum(nanvl('unitprice',lit(0))).alias('item_value'),
            sum(nanvl('totaldiscount',lit(0))).alias('order_discount'))


        insertIntoPartitionTable(df_client_metrics.filter(df_client_metrics.date.isNotNull()),'append',url,'tbl_fact_client',properties)

setInfo('--> Executado com sucesso')
sys.exit(0)