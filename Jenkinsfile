pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Pull the code from the repository
                checkout scm
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
