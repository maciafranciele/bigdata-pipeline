FROM maven:3.3-jdk-8

EXPOSE 8080

COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/

RUN mvn package

ENTRYPOINT ["mvn", "spring-boot:run"]