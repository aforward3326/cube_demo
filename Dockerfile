FROM openjdk:17-alpine
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} cubeDemo.war
ENTRYPOINT ["java","-jar","/cubeDemo.war"]

