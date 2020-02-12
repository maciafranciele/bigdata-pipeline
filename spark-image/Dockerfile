FROM jupyter/all-spark-notebook

MAINTAINER Macia Franciele <macia_fran@hotmail.com

USER root

RUN apt-get update && apt-get install -y build-essential curl git gnupg2 nano apt-transport-https software-properties-common


RUN wget https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk/1.7.4/aws-java-sdk-1.7.4.jar -P $SPARK_HOME/jars/
RUN wget https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-aws/2.7.3/hadoop-aws-2.7.3.jar -P $SPARK_HOME/jars/
RUN wget https://jdbc.postgresql.org/download/postgresql-42.2.6.jar -P $SPARK_HOME/jars/
RUN wget https://repo1.maven.org/maven2/org/apache/kafka/kafka-clients/2.4.0/kafka-clients-2.4.0.jar -P $SPARK_HOME/jars/
RUN wget https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-core/2.9.1/log4j-core-2.9.1.jar  -P $SPARK_HOME/jars/

USER $NB_USER

COPY requirements.txt /home/jovyan/
RUN pip install -r /home/jovyan/requirements.txt

#RUN python -c "import nltk; nltk.download('popular')"

COPY flow_ingest.sh /home/jovyan/
COPY ingestion_dim_merchant.py /home/jovyan/
COPY ingestion_dim_customer.py /home/jovyan/
COPY ingestion_dim_order.py /home/jovyan/
COPY ingestion_fact.py /home/jovyan/

#RUN jupyter contrib nbextension install --user
#RUN jupyter nbextensions_configurator enable --user

CMD [ "/home/jovyan/flow_ingest.sh" ]