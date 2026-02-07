pipeline {
    agent any

    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to fetch tags from')
        string(name: 'JAVA_VERSION', defaultValue: '17', description: 'Java version to use for the build')
    }

    stages {
        stage('Environment Setup and Debug') {
            steps {
                script {
                    try {
                        // Fetch and display the latest tags for the current branch, main, and dev
                        def currentTag = sh(script: "git fetch --tags && git describe --tags --abbrev=0 || echo 'No tags found'", returnStdout: true).trim()
                        def mainTag = sh(script: "git fetch origin main && git describe --tags origin/main --abbrev=0 || echo 'No main tag found'", returnStdout: true).trim()
                        def devTag = sh(script: "git fetch origin dev && git describe --tags origin/dev --abbrev=0 || echo 'No dev tag found'", returnStdout: true).trim()

                        echo "====================="
                        echo "Current branch tag: ${currentTag}"
                        echo "Main branch tag: ${mainTag}"
                        echo "Dev branch tag: ${devTag}"
                        echo "====================="

                        // Check Java installation
                        echo "Checking Java installation..."
                        sh "java -version || echo 'Java is not installed.'"
                        echo "Installing Java version: ${params.JAVA_VERSION}"
                        sh "sudo apt-get update && sudo apt-get install -y openjdk-${params.JAVA_VERSION}-jdk || error('Java installation failed.')"
                    } catch (Exception e) {
                        echo "Error during environment setup: ${e.getMessage()}"
                        error("Environment setup failed. Ensure all dependencies are installed.")
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    try {
                        echo "Building the Java project..."
                        sh 'javac -d out src/Main.java'
                    } catch (Exception e) {
                        echo "Build failed: ${e.getMessage()}"
                        error("Compilation error. Check the Java source files.")
                    }
                }
            }
        }
        stage('Run') {
            steps {
                script {
                    try {
                        echo "Running the Java program..."
                        sh 'java -cp out Main'
                    } catch (Exception e) {
                        echo "Execution failed: ${e.getMessage()}"
                        error("Runtime error. Check the Java program output.")
                    }
                }
            }
        }
    }
}
