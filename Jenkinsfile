pipeline {
    agent any

    environment {
        DOCKER_BIN = "/usr/local/bin/docker" // Path to the Docker binary
        PATH = "${env.PATH}:${env.DOCKER_BIN.substring(0, env.DOCKER_BIN.lastIndexOf('/'))}" // Add the directory of DOCKER_BIN to the PATH globally
    }

    stages {
        stage('Debug and Info') {
            steps {
                script {
                    echo "Gathering system information and debugging..."

                    // System Information
                    sh '''
                        echo "Path: $PATH"
                        echo "Docker binary: $DOCKER_BIN"
                        which docker
                        docker --version
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

                    // Docker Debugging
                    sh '''
                        echo "Checking Docker containers..."
                        docker ps -a
                        echo "Stopping any existing container on port 80..."
                        existing_container=$(docker ps --filter "publish=80" --format "{{.ID}}")
                        if [ ! -z "$existing_container" ]; then
                            docker stop $existing_container
                            docker rm $existing_container
                        fi
                    '''

                    // Build Docker Image
                    echo "Building the Docker image..."
                    sh '''
                        docker build -t my-java-nginx-project:latest -f Dockerfile .
                        docker ps -a
                    '''

                    // Run Docker Container
                    echo "Running the Docker container..."
                    sh '''
                        docker run -d -p 80:80 --name my-java-nginx-project my-java-nginx-project:latest
                        docker ps -a
                    '''

                    // Inspect Nginx Container
                    echo "Inspecting the Nginx container..."
                    sh '''
                        docker exec my-java-nginx-project ls -l /usr/share/nginx/html
                        docker exec my-java-nginx-project cat /usr/share/nginx/html/index.html || echo "index.html not found"
                        docker exec my-java-nginx-project cat /etc/nginx/nginx.conf
                        docker logs my-java-nginx-project
                    '''

                    // Build and Run Java Project
                    echo "Building and running the Java project..."
                    sh '''
                        javac -d out src/Main.java
                        java -cp out Main
                    '''
                }
            }
        }
    }
}
