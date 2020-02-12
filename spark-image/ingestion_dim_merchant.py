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
    format= '%(asctime)s: MERCHANT_DIM_INGESTION %(message)s'
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
        .appName('merchant_dim_ingestion') \
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
        #MERCHANT
        #----------------------------------------------------------

        merchant_schema = selectSchema(url,"tbl_dim_merchant",properties)

        merchant_scruc = StructType(fields=merchant_schema)

        df_merchant = spark.read.csv("s3a://ifood-data-architect-test-source/restaurant.csv.gz",header=True, schema=merchant_scruc).persist()

        df_merchant_curated = df_merchant.na.fill({'minimum_order_value': 0, 'delivery_time': 0})

        insertIntoTable(df_merchant_curated,'append',url,'tbl_dim_merchant',properties)

setInfo('--> Executado com sucesso')
sys.exit(0)