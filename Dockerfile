# --- Etapa 1: Compilación (Build) ---
# Usamos una imagen de Maven con JDK 21 para compilar
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app

# Copiamos solo el pom para descargar las dependencias y aprovechar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y generamos el JAR
COPY src ./src
RUN mvn clean package -DskipTests

# --- Etapa 2: Ejecución (Runtime) ---
# Usamos un JRE de Java 21 ligero para correr la app
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiamos el JAR generado. Según tu pom, el nombre será weatherapp-0.0.1-SNAPSHOT.jar
COPY --from=build /app/target/weatherapp-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Configuramos Java para el entorno de Render:
# 1. Definimos el puerto dinámico con -Dserver.port
# 2. Limitamos la memoria RAM (-Xmx) para que no exceda los 512MB de la capa gratuita
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-Xmx400m", "-jar", "app.jar"]