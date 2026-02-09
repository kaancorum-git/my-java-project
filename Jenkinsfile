pipeline {
    agent any

    environment {
        PROJECT_NAME = "my-java-nginx-project" // The name of the project
        DOCKER_BIN = "/usr/local/bin/docker" // Path to the Docker binary
        PATH = "${env.PATH}:${env.DOCKER_BIN.substring(0, env.DOCKER_BIN.lastIndexOf('/'))}" // Add the directory of DOCKER_BIN to the PATH globally
        DOCKER_HUB_CREDENTIALS_USR = "kncrm" // Docker Hub username
    }

    stages {
        stage('Info') {
            steps {
                script {
                    echo "Gathering system information..."
                    sh '''
                        echo "Path: $PATH"
                        echo "Docker binary: $DOCKER_BIN"
                        echo "Project name: $PROJECT_NAME"
                        which docker
                        docker --version
                    '''
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building the Docker image..."
                    sh '''
                        docker build -t ${PROJECT_NAME}:latest -f Dockerfile .
                    '''
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo "Tagging and pushing the Docker image to Docker Hub..."
                    sh '''
                        docker tag ${PROJECT_NAME}:latest ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:latest
                        docker push ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:latest
                    '''
                }
            }
        }
        stage('Pull and Run Docker Image') {
            steps {
                script {
                    echo "Pulling the Docker image from Docker Hub..."
                    sh '''
                        docker pull ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:latest
                    '''

                    echo "Stopping any existing container..."
                    sh '''
                        existing_container=$(docker ps --filter "name=${PROJECT_NAME}" --format "{{.ID}}")
                        if [ ! -z "$existing_container" ]; then
                            docker stop $existing_container
                            docker rm $existing_container
                        fi
                    '''

                    echo "Running the Docker container..."
                    sh '''
                        docker run -d -p 80:80 --name ${PROJECT_NAME} ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:latest
                        docker ps -a
                    '''
                }
            }
        }
    }
}
