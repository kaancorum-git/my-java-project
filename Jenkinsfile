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
