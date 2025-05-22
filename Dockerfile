# Imagen base con JDK 17 en Alpine (liviana)
FROM eclipse-temurin:21-jdk-alpine

# Copia el archivo JAR a la imagen
COPY target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 (Spring Boot usa este por defecto)
EXPOSE 8081

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]