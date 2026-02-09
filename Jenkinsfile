pipeline {
    agent any

    environment {
        PROJECT_NAME = "my-java-nginx-project" // The base name of the project
        DOCKER_BIN = "/usr/local/bin/docker" // Path to the Docker binary
        PATH = "${env.PATH}:${env.DOCKER_BIN.substring(0, env.DOCKER_BIN.lastIndexOf('/'))}" // Add the directory of DOCKER_BIN to the PATH globally
        DOCKER_HUB_CREDENTIALS_USR = "kncrm" // Docker Hub username
    }

    stages {
        stage('Debug Environment Variables') {
            steps {
                script {
                    echo "Printing all environment variables..."
                    sh 'printenv | sort'
                    echo "Git version:"
                    sh 'git --version'
                    echo "Branch Name: ${env.BRANCH_NAME}"
                }
            }
        }
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out the code..."
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/${env.BRANCH_NAME}']],
                        userRemoteConfigs: [[
                            url: 'https://github.com/kaancorum/my-java-project.git',
                            refspec: '+refs/heads/*:refs/remotes/origin/*'
                        ]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [[$class: 'LocalBranch', localBranch: '${env.BRANCH_NAME}']] // Ensure the branch is checked out locally
                    ])
                }
            }
        }
        stage('Retrieve Branch Name') {
            steps {
                script {
                    // Dynamically retrieve the branch name using git
                    env.BRANCH_NAME = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
                    echo "Branch Name: ${env.BRANCH_NAME}"
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
