pipeline {
    agent any

    environment {
        PROJECT_NAME = "my-java-spring-project"
        //DOCKER_BIN = "/usr/local/bin/docker" // Docker'ın yolu
        DOCKER_HOME = "/usr/local"
        MAVEN_HOME = "/opt/homebrew" // Maven'ın yolu
        //PATH = "${env.PATH}:${env.DOCKER_BIN.substring(0, env.DOCKER_BIN.lastIndexOf('/'))}:${env.MAVEN_BIN.substring(0, env.MAVEN_BIN.lastIndexOf('/'))}" // PATH'e Maven ve Docker ekle
        DOCKER_HUB_CREDENTIALS_USR = "kncrm"
        BRANCH_NAME = "${env.BRANCH_NAME}"
    }

    stages {
        stage('Debug Environment Variables') {
            steps {
                script {
                    sh 'uname -a'
	•	            sh 'ls -la /opt/homebrew'
                    sh 'which mvn || true'
                    echo "Printing all environment variables..."
                    sh 'printenv | sort'
                    echo "Git version:"
                    sh 'git --version'
                    echo "Maven version:"
                    sh 'mvn -v' // mvn doğrudan çalışacak
                    echo "Docker version:"
                    sh 'docker --version' // docker doğrudan çalışacak
                    echo "Branch Name: ${env.BRANCH_NAME}"
                }
            }
        }
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out the code..."
                    checkout scm
                }
            }
        }
        stage('Build Spring Boot JAR') {
            steps {
                script {
                    echo "Building the Spring Boot JAR file..."
                    sh '''
                        mvn clean package // mvn doğrudan çalışacak
                    '''
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building the Docker image for branch: ${env.BRANCH_NAME}..."
                    sh '''
                        docker build -t ${PROJECT_NAME}:${BRANCH_NAME} -f Dockerfile .
                    '''
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo "Tagging and pushing the Docker image to Docker Hub..."
                    sh '''
                        docker tag ${PROJECT_NAME}:${BRANCH_NAME} ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:${BRANCH_NAME}
                        docker push ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:${BRANCH_NAME}
                    '''
                }
            }
        }
        stage('Pull and Run Docker Image') {
            steps {
                script {
                    echo "Pulling the Docker image from Docker Hub..."
                    sh '''
                        docker pull ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:${BRANCH_NAME}
                    '''

                    echo "Stopping any existing container..."
                    sh '''
                        existing_container=$(docker ps --filter "name=${PROJECT_NAME}" --format "{{.ID}}")
                        if [ ! -z "$existing_container" ];then
                            docker stop $existing_container
                            docker rm $existing_container
                        fi
                    '''

                    echo "Running the Docker container..."
                    sh '''
                        docker run -d -p 80:80 --name ${PROJECT_NAME} ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:${BRANCH_NAME}
                        docker ps -a
                    '''
                }
            }
        }
    }
}
