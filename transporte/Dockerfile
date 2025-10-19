# Imagen base de Java 17 mantenida por Amazon
FROM amazoncorretto:17-alpine-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR generado al contenedor
COPY target/bank_inc-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto (por defecto Spring Boot usa 8080)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]