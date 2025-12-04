
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#Run with JDK
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
VOLUME /app/logs
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
