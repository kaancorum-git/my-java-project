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
                    sh '''
                        echo "Path: $PATH"
                        echo "docker bin: $DOCKER_BIN"
                        which docker
                        docker --version
                    '''
                }
            }
        }
        stage('Debug Docker Access') {
            steps {
                script {
                    sh '''
                        echo "Updated PATH: $PATH"
                        which docker || echo "Docker binary not found"
                        docker --version || echo "Docker is not installed or not in PATH"
                        echo "docker ps"
                        docker ps -a
                    '''
                }
            }
        }
        stage('Check Docker Version') {
            steps {
                script {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        echo "Checking Docker version..."
                        sh 'docker --version || echo "Docker is not installed or not in PATH"'
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
                    echo "Building the Docker image..."
                    sh '''
                        docker build -t my-java-nginx-project:latest -f Dockerfile .
                    '''
                }
            }
        }
        stage('Run Docker Container') {
            steps {
                script {
                    echo "Running the Docker container..."
                    sh '''
                        docker run -d -p 80:80 --name my-java-nginx-project my-java-nginx-project:latest
                    '''
                }
            }
        }
        stage('Debug Nginx Container') {
            steps {
                script {
                    echo "Inspecting the Nginx container..."
                    sh '''
                        docker exec my-java-nginx-project ls -l /usr/share/nginx/html
                        docker exec my-java-nginx-project cat /usr/share/nginx/html/index.html || echo "index.html not found"
                        docker exec my-java-nginx-project cat /etc/nginx/nginx.conf
                        docker logs my-java-nginx-project
                    '''
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
