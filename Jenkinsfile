pipeline {
    agent any

    stages {
        stage('Environment Setup and Debug') {
            steps {
                script {
                    try {
                        // Fetch and display the latest tags for the current branch
                        def currentBranch = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
                        def currentTag = sh(script: "git fetch --tags && git describe --tags --abbrev=0 || echo 'No tags found'", returnStdout: true).trim()
                        def mainTag = sh(script: "git fetch origin main && git describe --tags origin/main --abbrev=0 || echo 'No main tag found'", returnStdout: true).trim()
                        def devTag = sh(script: "git fetch origin dev && git describe --tags origin/dev --abbrev=0 || echo 'No dev tag found'", returnStdout: true).trim()

                        echo "====================="
                        echo "Current branch: ${currentBranch}"
                        echo "Current branch tag: ${currentTag}"
                        echo "Main branch tag: ${mainTag}"
                        echo "Dev branch tag: ${devTag}"
                        echo "====================="

                        // Check Java installation and version
                        echo "Checking Java installation..."
                        def javaVersionOutput = sh(script: "java -version 2>&1", returnStdout: true).trim()
                        if (javaVersionOutput.contains('17.')) {
                            echo "Java 17 is already installed."
                        } else {
                            echo "Java 17 is not installed. Installing Java version: 17"
                            def installStatus = sh(script: "sudo apt-get update && sudo apt-get install -y openjdk-17-jdk", returnStatus: true)
                            if (installStatus != 0) {
                                error("Java installation failed. Ensure the system has access to the package manager.")
                            }
                        }
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
