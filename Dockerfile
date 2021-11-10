FROM openjdk:11
ARG JAR_FILE=docker_image/*.jar
COPY ${JAR_FILE} app.jar
COPY docker_image/application.properties application.properties
ENTRYPOINT ["java","-jar","/app.jar"]