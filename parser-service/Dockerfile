
FROM java:8-jre-alpine

COPY target/*.jar /opt/app.jar

WORKDIR /opt

ENTRYPOINT ["java", "-jar", "app.jar"]