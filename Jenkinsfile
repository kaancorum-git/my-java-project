pipeline {
    agent any

    environment {
        DOCKER_BIN = "/usr/local/bin/docker" // Path to the Docker binary
        PATH = "${env.PATH}:${env.DOCKER_BIN.substring(0, env.DOCKER_BIN.lastIndexOf('/'))}" // Add the directory of DOCKER_BIN to the PATH globally
    }

    stages {
        stage('Info') {
            steps {
                script {
                    echo "Gathering system information..."

                    // System Information
                    sh '''
                        echo "Path: $PATH"
                        echo "Docker binary: $DOCKER_BIN"
                        which docker
                        docker --version
                        echo "Current User: $(whoami)"
                        echo "Home Directory: $HOME"
                        echo "Current Directory: $(pwd)"
                        echo "Environment Variables:"
                        printenv
                        echo "Available Disk Space:"
                        df -h
                        echo "Memory Usage:"
                        free -h || vm_stat || echo "Memory info not available"
                        echo "Java Version:"
                        java -version || echo "Java is not installed"
                        echo "Git Version:"
                        git --version || echo "Git is not installed"
                    '''
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building the Docker image..."
                    sh '''
                        docker ps -a
                        docker build -t my-java-nginx-project:latest -f Dockerfile .
                        docker ps -a
                    '''
                }
            }
        }
        stage('Run Docker Container') {
            steps {
                script {
                    echo "Running the Docker container..."
                    sh '''
                        docker ps -a
                        docker run -d -p 80:80 --name my-java-nginx-project my-java-nginx-project:latest
                        docker ps -a
                    '''
                }
            }
        }
        stage('Build Java Project') {
            steps {
                script {
                    echo "Building the Java project..."
                    sh '''
                        javac -d out src/Main.java
                        echo "Java build completed."
                    '''
                }
            }
        }
        stage('Run Java Project') {
            steps {
                script {
                    echo "Running the Java program..."
                    sh '''
                        java -cp out Main
                        echo "Java program execution completed."
                    '''
                }
            }
        }
    }
}
