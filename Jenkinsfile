pipeline {
    agent any

    stages {
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
