#FOR OWN SERVER DEPLOY
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-Xmx400M", "-jar", "app.jar"]

#FOR RENDER DEPLOY
#FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
#WORKDIR /app
#
#COPY pom.xml .
#RUN mvn dependency:go-offline
#
#COPY src ./src
#RUN mvn clean package -DskipTests
#
#FROM eclipse-temurin:21-jre-jammy
#WORKDIR /app
#
#COPY --from=build /app/target/weatherapp-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-Xmx400m", "-jar", "app.jar"]