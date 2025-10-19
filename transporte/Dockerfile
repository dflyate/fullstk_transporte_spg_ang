# Etapa 1: build
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: ejecución
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=build /app/target/transporte-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]