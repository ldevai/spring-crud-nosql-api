FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=target/*.jar
ARG APP_ENV=dev
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${APP_ENV}","-jar","/app.jar"]

# Use the lines below to refer to a properties file outside the jar
#ENTRYPOINT ["java","-D--spring.config.location=/application-prod-protected .yml","-jar","/app.jar"]
