#!/bin/bash
################################################################################
## Nome: flow_ingest.sh
## Description: pyspark para ingestao das tabelas
################################################################################

## Informacoes principais
SCRIPT_NAME="flow_ingest"
SCRIPT_PATH="/home/jovyan/"
DEBUG="y" #y or n

## Configuracoes spark
SPARK_HOME=/usr/local/spark
SPARK_MASTER=local[8]

##AWS credentials

function ingestion_merchant() {

  JOB_NAME="ingestion_merchant"
	echo "[INFO] - Executando funcao ingestion_merchant()"
	
  echo "[INFO] - Executando spark submit para o job ${JOB_NAME}."
	${SPARK_HOME}/bin/spark-submit \
    --master ${SPARK_MASTER} \
		--conf spark.sql.warehouse.dir=myWareHouseDir \
    --name ${JOB_NAME} ${SCRIPT_PATH}ingestion_dim_merchant.py ${ACCESS_KEY} ${SECRET_KEY}
	
    if [ $? == 0 ]
    then
		echo "[INFO] - ${JOB_NAME} finalizado com sucesso."
    else
        echo "[ERRO] - Falha ao realizar processamento"
		exit 1
    fi

}

function ingestion_customer(){

  JOB_NAME="ingestion_customer"
	echo "[INFO] - Executando funcao ingestion_customer()"

  echo "[INFO] - Executando spark submit para o job ${JOB_NAME}."
	${SPARK_HOME}/bin/spark-submit \
    --master ${SPARK_MASTER} \
		--conf spark.sql.warehouse.dir=myWareHouseDir \
    --name ${JOB_NAME} ${SCRIPT_PATH}ingestion_dim_customer.py ${ACCESS_KEY} ${SECRET_KEY}

    if [ $? == 0 ]
    then
		echo "[INFO] - ${JOB_NAME} finalizado com sucesso."
    else
        echo "[ERRO] - Falha ao realizar processamento"
		exit 1
    fi

}


function ingestion_order(){

  JOB_NAME="ingestion_order"
	echo "[INFO] - Executando funcao ingestion_order()"

  echo "[INFO] - Executando spark submit para o job ${JOB_NAME}."
	${SPARK_HOME}/bin/spark-submit \
    --master ${SPARK_MASTER} \
		--conf spark.sql.warehouse.dir=myWareHouseDir \
    --name ${JOB_NAME} ${SCRIPT_PATH}ingestion_dim_order.py ${ACCESS_KEY} ${SECRET_KEY}

    if [ $? == 0 ]
    then
		echo "[INFO] - ${JOB_NAME} finalizado com sucesso."
    else
        echo "[ERRO] - Falha ao realizar processamento"
		exit 1
    fi

}

function ingestion_fact(){

  JOB_NAME="ingestion_order"
	echo "[INFO] - Executando funcao ingestion_fact()"

  echo "[INFO] - Executando spark submit para o job ${JOB_NAME}."
	${SPARK_HOME}/bin/spark-submit \
    --master ${SPARK_MASTER} \
		--conf spark.sql.warehouse.dir=myWareHouseDir \
    --name ${JOB_NAME} ${SCRIPT_PATH}ingestion_fact.py ${ACCESS_KEY} ${SECRET_KEY}

    if [ $? == 0 ]
    then
		echo "[INFO] - ${JOB_NAME} finalizado com sucesso."
    else
        echo "[ERRO] - Falha ao realizar processamento"
		exit 1
    fi

}


function main() {
     echo "-----------------------------------------------------------------"
     echo "[INFO] - Inicio do Fluxo ${SCRIPT_NAME}.sh -"
     
     DATE_START=`date -u +%s`

      ingestion_merchant
      ingestion_customer
      ingestion_order
	  ingestion_fact
     
     DATE_STOP=`date -u +%s`
     DATE_DURATION=`expr \( $DATE_STOP - $DATE_START \) / 60`
     
     echo "$SCRIPT_NAME" "Success" "$DATE_DURATION"
     
     echo "[INFO] - Fim da carga do script ${SCRIPT_NAME}.sh"
     echo "-----------------------------------------------------------------"
}

###  ..:: Fluxo ::..

[ "$DEBUG" == "n" ] && main &> /dev/null || main

exit 0

################################################################################

## ..:: Fim da execucao ::..
