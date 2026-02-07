pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Pull the code from the repository
                checkout scm
            }
        }
        stage('Debug Info') {
            steps {
                // Fetch and display the latest tags for the current branch, main, and dev
                script {
                    def currentTag = sh(script: "git fetch --tags && git describe --tags --abbrev=0 || echo 'No tags found'", returnStdout: true).trim()
                    def mainTag = sh(script: "git fetch origin main && git describe --tags origin/main --abbrev=0 || echo 'No main tag found'", returnStdout: true).trim()
                    def devTag = sh(script: "git fetch origin dev && git describe --tags origin/dev --abbrev=0 || echo 'No dev tag found'", returnStdout: true).trim()

                    echo "====================="
                    echo "Current branch tag: ${currentTag}"
                    echo "Main branch tag: ${mainTag}"
                    echo "Dev branch tag: ${devTag}"
                    echo "====================="
                }
            }
        }
        stage('Build') {
            steps {
                // Compile the Java code
                sh 'javac -d out src/Main.java'
            }
        }
        stage('Run') {
            steps {
                // Run the compiled Java program
                sh 'java -cp out Main'
            }
        }
    }
}
