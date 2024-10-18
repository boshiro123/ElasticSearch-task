FROM maven:3.9.5-eclipse-temurin-21-alpine as build
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
