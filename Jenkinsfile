pipeline {
    agent any

    environment {
        PROJECT_NAME = "my-java-nginx-project" // The name of the project
        DOCKER_BIN = "/usr/local/bin/docker" // Path to the Docker binary
        PATH = "${env.PATH}:${env.DOCKER_BIN.substring(0, env.DOCKER_BIN.lastIndexOf('/'))}" // Add the directory of DOCKER_BIN to the PATH globally
        //DOCKER_HUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIALS') // Jenkins credentials ID for Docker Hub
        DOCKER_HUB_CREDENTIALS_USR="kncrm"
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
                    echo "Logging in to Docker Hub..."
                    // echo $DOCKER_HUB_CREDENTIALS_PSW | docker login -u $DOCKER_HUB_CREDENTIALS_USR --password-stdin
                    // echo "Tagging and pushing the Docker image to Docker Hub..."
                    sh '''
                        docker tag ${PROJECT_NAME}:latest ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:latest
                        docker push ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:latest
                    '''
                }
            }
        }
    }
}
