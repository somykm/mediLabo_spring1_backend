# Use a base image with Java 17 already installed
FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8081
VOLUME /app/logs

# Set working directory
WORKDIR /tmp
# Copy the built JAR file into the container
COPY ${JAR_FILE} app.jar
ARG JAR_FILE=target/*.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]