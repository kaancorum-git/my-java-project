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
                sh '''
                git fetch --tags
                CURRENT_TAG=$(git describe --tags --abbrev=0 || echo "No tags found")
                MAIN_TAG=$(git tag --sort=-v:refname | grep '^main-' | head -n 1 || echo "No main tag found")
                DEV_TAG=$(git tag --sort=-v:refname | grep '^dev-' | head -n 1 || echo "No dev tag found")

                echo "====================="
                echo "Current branch tag: $CURRENT_TAG"
                echo "Main branch tag: $MAIN_TAG"
                echo "Dev branch tag: $DEV_TAG"
                echo "====================="
                '''
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
