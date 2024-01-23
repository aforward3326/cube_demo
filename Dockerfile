
FROM openjdk:17-oracle
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} cubeDemo.war
ARG WEB_PASSWORD
ENV WEB_PASSWORD=${WEB_PASSWORD}
ENTRYPOINT ["java","-jar","/cubeDemo.war"]

