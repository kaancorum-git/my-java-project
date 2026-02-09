# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven build output (JAR file) to the container
COPY target/my-java-project-1.0.0.jar app.jar

# Expose port 80 for the Spring Boot application
EXPOSE 80

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
