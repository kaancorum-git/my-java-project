pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "my-java-nginx-project"
        DOCKER_BIN = "/usr/local/bin/docker"
    }

    stages {
        stage('Debug Docker Access') {
            steps {
                script {
                    try {
                        echo "Checking Docker installation..."
                        sh '${DOCKER_BIN} --version || echo "Docker is not installed or not in PATH"'
                        sh 'which docker || echo "Docker binary not found"'
                        sh '${DOCKER_BIN} ps || echo "Docker daemon is not running or Jenkins user lacks permissions"'
                    } catch (Exception e) {
                        echo "Docker debug failed: ${e.getMessage()}"
                        error("Failed to verify Docker access.")
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        echo "Building the Docker image with Java 17 and Nginx..."
                        sh "${DOCKER_BIN} build -t ${DOCKER_IMAGE_NAME}:latest ."
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
                        sh "${DOCKER_BIN} run -d -p 80:80 --name ${DOCKER_IMAGE_NAME} ${DOCKER_IMAGE_NAME}:latest"
                    } catch (Exception e) {
                        echo "Docker run failed: ${e.getMessage()}"
                        error("Failed to run the Docker container.")
                    }
                }
            }
        }
    }
}
