pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "my-java-nginx-project"
    }

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        echo "Building the Docker image with Java 17 and Nginx..."
                        sh "docker build -t ${DOCKER_IMAGE_NAME}:latest ."
                    } catch (Exception e) {
                        echo "Docker build failed: ${e.getMessage()}"
                        error("Failed to build the Docker image.")
                    }
                }
            }
        }
        stage('Run Docker Container') {
            steps {
                script {
                    try {
                        echo "Running the Docker container..."
                        sh "docker run -d -p 80:80 --name ${DOCKER_IMAGE_NAME} ${DOCKER_IMAGE_NAME}:latest"
                    } catch (Exception e) {
                        echo "Docker run failed: ${e.getMessage()}"
                        error("Failed to run the Docker container.")
                    }
                }
            }
        }
    }
}
