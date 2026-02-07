# Use the official OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install Nginx
RUN apt-get update && apt-get install -y nginx && \
    rm -rf /var/lib/apt/lists/*

# Copy the Java application source code
COPY src /app/src

# Compile the Java code
RUN javac -d out src/Main.java

# Copy a basic Nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 80 for Nginx
EXPOSE 80

# Start both Nginx and the Java application
CMD ["sh", "-c", "service nginx start && java -cp out Main"]
