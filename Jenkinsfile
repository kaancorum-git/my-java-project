pipeline {
    agent any

    stages {
        stage('Info') {
            steps {
                script {
                    echo "Gathering system information..."
                    sh '''
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
                        echo "Docker BIN:"
                        echo "DOCKER_BIN: $DOCKER_BIN"
                    '''
                }
            }
        }
        stage('Debug Docker Access') {
            steps {
                script {
                    sh 'echo $PATH'
                    sh 'which docker || echo "Docker binary not found"'
                    sh 'docker --version || echo "Docker is not installed or not in PATH"'
                }
            }
        }
        stage('Check Docker Version') {
            steps {
                script {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        echo "Checking Docker version..."
                        sh '$DOCKER_BIN --version || echo "Docker is not installed or not in PATH"'
                    }
                }
            }
        }
        stage('Stop Existing Container') {
            steps {
                script {
                    echo "Checking if port 80 is in use..."
                    def portInUse = sh(script: "lsof -i :80 || true", returnStdout: true).trim()
                    if (portInUse) {
                        echo "Port 80 is in use. Stopping any existing container using port 80..."
                        sh '''
                            existing_container=$(docker ps --filter "publish=80" --format "{{.ID}}")
                            if [ ! -z "$existing_container" ]; then
                                docker stop $existing_container
                                docker rm $existing_container
                            fi
                        '''
                    } else {
                        echo "Port 80 is available."
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        echo "Building the Docker image with the specified Dockerfile..."
                        sh '$DOCKER_BIN build -t my-java-nginx-project:latest -f Dockerfile .'
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
                        echo "Running the Docker container on port 80..."
                        sh '$DOCKER_BIN run -d -p 80:80 --name my-java-nginx-project my-java-nginx-project:latest'
                    } catch (Exception e) {
                        echo "Docker run failed: ${e.getMessage()}"
                        error("Failed to run the Docker container.")
                    }
                }
            }
        }
        stage('Build Java Project') {
            steps {
                script {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        echo "Building the Java project..."
                        sh 'javac -d out src/Main.java'
                    }
                }
            }
        }
        stage('Run Java Project') {
            steps {
                script {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        echo "Running the Java program..."
                        sh 'java -cp out Main'
                    }
                }
            }
        }
    }
}
