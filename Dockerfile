# Use a valid OpenJDK base image
FROM amazoncorretto:17

# Set the working directory
WORKDIR /app

# Print the current working directory
RUN pwd

# Debugging: List files in the target directory
RUN ls -l

# Copy the repackaged JAR file to the container
COPY target/my-java-project-1.0.0.jar app.jar

# Expose port 80 for the Spring Boot application
EXPOSE 80

# Add a health check to verify the application is running
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:80/actuator/health || exit 1

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
