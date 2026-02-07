pipeline {
    agent any

    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to fetch tags from')
        string(name: 'JAVA_VERSION', defaultValue: '17', description: 'Java version to use for the build')
    }

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
                    try {
                        def currentTag = sh(script: "git fetch --tags && git describe --tags --abbrev=0 || echo 'No tags found'", returnStdout: true).trim()
                        def mainTag = sh(script: "git fetch origin main && git describe --tags origin/main --abbrev=0 || echo 'No main tag found'", returnStdout: true).trim()
                        def devTag = sh(script: "git fetch origin dev && git describe --tags origin/dev --abbrev=0 || echo 'No dev tag found'", returnStdout: true).trim()

                        echo "====================="
                        echo "Current branch tag: ${currentTag}"
                        echo "Main branch tag: ${mainTag}"
                        echo "Dev branch tag: ${devTag}"
                        echo "====================="
                    } catch (Exception e) {
                        echo "Error fetching tags: ${e.getMessage()}"
                        error("Failed to fetch tags. Ensure the repository has valid tags.")
                    }
                }
            }
        }
        stage('Set Up Java') {
            steps {
                // Set up Java dynamically based on the parameter
                script {
                    echo "Setting up Java version: ${params.JAVA_VERSION}"
                }
                sh "sdk install java ${params.JAVA_VERSION} || echo 'Java installation failed. Ensure SDKMAN is installed.'"
            }
        }
        stage('Build') {
            steps {
                // Compile the Java code
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
                // Run the compiled Java program
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
