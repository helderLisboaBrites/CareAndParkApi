FROM openjdk:11
COPY target/*.jar app.jar
COPY src/main/resources/application.properties application.properties
ENTRYPOINT ["java","-jar","/app.jar"]