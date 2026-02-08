pipeline {
    agent any

    stages {
        stage('Check Docker Version') {
            steps {
                script {
                    try {
                        echo "Checking Docker version..."
                        sh 'docker --version || echo "Docker is not installed or not in PATH"'
                    } catch (Exception e) {
                        echo "Docker version check failed: ${e.getMessage()}"
                        error("Failed to verify Docker installation.")
                    }
                }
            }
        }
    }
}
