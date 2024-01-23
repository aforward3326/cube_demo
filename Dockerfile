FROM openjdk:17-oracle
ARG JAR_FILE=target/*
COPY ${JAR_FILE} cubeDemo.war
ENTRYPOINT ["java","-jar","/cubeDemo.war"]

