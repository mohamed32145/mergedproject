FROM openjdk:21-jdk-alpine

VOLUME /tmp

ARG JAR_FILE='target/*.jar'

COPY ${JAR_FILE} loader.jar

ENTRYPOINT ["java","-jar","/loader.jar"]
