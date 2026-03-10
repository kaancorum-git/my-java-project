pipeline {
    agent any

    parameters {
        booleanParam(name: 'DEPLOY_TO_K8S', defaultValue: false, description: 'Enable optional Kubernetes deployment after Docker deployment')
        string(name: 'DOCKER_SMOKE_BASE_URL', defaultValue: 'http://localhost:8081', description: 'Base URL for Docker smoke tests')
        string(name: 'K8S_NAMESPACE', defaultValue: 'default', description: 'Kubernetes namespace for deployment')
        string(name: 'KUBECONFIG_CREDENTIALS_ID', defaultValue: '', description: 'Jenkins file credential ID that contains kubeconfig')
        string(name: 'K8S_IMAGE_TAG', defaultValue: '', description: 'Optional image tag override for Kubernetes deployment (default: BRANCH_NAME)')
    }

    environment {

        PROJECT_NAME = "my-java-spring-boot-project"
        JAVA_HOME  = "/opt/homebrew/opt/openjdk@17"
        DOCKER_BIN = "/usr/local/bin/docker"
        MAVEN_BIN  = "/opt/homebrew/bin/mvn"
        KUBECTL_BIN = "/usr/local/bin/kubectl"
        PATH = "${env.DOCKER_BIN.substring(0, env.DOCKER_BIN.lastIndexOf('/'))}:" +
           "${env.MAVEN_BIN.substring(0, env.MAVEN_BIN.lastIndexOf('/'))}:" +
           "${env.KUBECTL_BIN.substring(0, env.KUBECTL_BIN.lastIndexOf('/'))}:" +
           "${env.PATH}"
        //PATH = "${env.PATH}:${env.MAVEN_BIN.substring(0, env.MAVEN_BIN.lastIndexOf('/'))}" // PATH'e Maven'ın bin dizinini ekle
        DOCKER_HUB_CREDENTIALS_USR = "kncrm"
        BRANCH_NAME = "${env.BRANCH_NAME}"
    }

    stages {
        stage('Debug Environment Variables') {
            steps {
                script {
                    sh 'uname -a'
                    sh 'ls -la /opt/homebrew'
                    sh 'which mvn || true'
                    echo "Printing all environment variables..."
                    sh 'printenv | sort'
                    echo "Git version:"
                    sh 'git --version'
                    echo "Maven version:"
                    sh 'mvn -v' // MAVEN_BIN kullanıldı
                    echo "Docker version:"
                    sh 'docker --version'
                    sh 'which docker'
                    echo "Branch Name: ${env.BRANCH_NAME}"
                }
            }
        }
        stage('Prepare Build Info') {
            steps {
                script {
                    echo "Writing build info..."
                    sh '''
                        echo "build.number=${BUILD_NUMBER}" > build-info.properties
                        echo "branch.name=${BRANCH_NAME}" >> build-info.properties
                    '''
                }
            }
        }
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out the code..."
                    checkout scm
                }
            }
        }
        stage('Build Spring Boot JAR') {
            steps {
                script {
                    echo "Building the Spring Boot JAR file..."
                    sh '''
                        ${MAVEN_BIN} clean package
                    '''
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building the Docker image for branch: ${env.BRANCH_NAME}..."
                    sh '''
                        docker build --build-arg BUILD_NUMBER=${BUILD_NUMBER} --build-arg BRANCH_NAME=${BRANCH_NAME} -t ${PROJECT_NAME}:${BRANCH_NAME} -f Dockerfile .
                    '''
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo "Tagging and pushing the Docker image to Docker Hub..."
                    sh '''
                        docker tag ${PROJECT_NAME}:${BRANCH_NAME} ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:${BRANCH_NAME}
                        docker push ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:${BRANCH_NAME}
                    '''
                }
            }
        }
        stage('Pull and Run Docker Image') {
            steps {
                script {
                    echo "Pulling the Docker image from Docker Hub..."
                    sh '''
                        docker pull ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:${BRANCH_NAME}
                    '''

                    echo "Stopping any existing container..."
                    sh '''
                        existing_container=$(docker ps -a --filter "name=${PROJECT_NAME}" --format "{{.ID}}")
                        if [ ! -z "$existing_container" ]; then
                            docker stop $existing_container || true
                            docker rm $existing_container || true
                        fi

                        # Safety guard: stop/remove any container publishing host port 8081
                        port_8081_containers=$(docker ps -aq --filter "publish=8081")
                        if [ ! -z "$port_8081_containers" ]; then
                            echo "Found containers publishing port 8081. Cleaning up..."
                            docker stop $port_8081_containers || true
                            docker rm $port_8081_containers || true
                        fi

                        # If monitoring stack exists, stop and remove before fresh start
                        docker compose down --remove-orphans || true

                        # Backward compatibility cleanup for old fixed compose container names
                        docker rm -f portfolio-prometheus portfolio-grafana portfolio-app 2>/dev/null || true

                        # Final check before run
                        in_use_after_cleanup=$(docker ps -q --filter "publish=8081")
                        if [ ! -z "$in_use_after_cleanup" ]; then
                            echo "ERROR: Port 8081 is still in use after cleanup."
                            docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Ports}}"
                            exit 1
                        fi
                    '''

                    echo "Running the Docker container..."
                    sh '''
                        docker run -d -p 8081:8081 --name ${PROJECT_NAME} ${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:${BRANCH_NAME}
                        docker compose up -d --no-deps prometheus grafana
                        docker ps -a
                    '''
                }
            }
        }

        stage('Smoke Test - Docker Deployment') {
            steps {
                script {
                    sh '''
                        set -e
                        echo "Waiting for Docker deployment to become healthy..."

                        for i in $(seq 1 30); do
                            if curl -fsS ${DOCKER_SMOKE_BASE_URL}/actuator/health >/dev/null 2>&1; then
                                echo "Docker app is reachable."
                                break
                            fi
                            if [ "$i" -eq 30 ]; then
                                echo "ERROR: Docker app did not become reachable on /actuator/health in time."
                                docker ps -a
                                docker logs ${PROJECT_NAME} --tail 200 || true
                                exit 1
                            fi
                            sleep 2
                        done

                        liveness_status=$(curl -fsS ${DOCKER_SMOKE_BASE_URL}/actuator/health/liveness | grep -o '"status":"[A-Z]*"' | head -n1 | cut -d':' -f2 | tr -d '"')
                        readiness_status=$(curl -fsS ${DOCKER_SMOKE_BASE_URL}/actuator/health/readiness | grep -o '"status":"[A-Z]*"' | head -n1 | cut -d':' -f2 | tr -d '"')

                        echo "Docker liveness status: ${liveness_status}"
                        echo "Docker readiness status: ${readiness_status}"

                        if [ "${liveness_status}" != "UP" ] || [ "${readiness_status}" != "UP" ]; then
                            echo "ERROR: Docker smoke tests failed."
                            docker logs ${PROJECT_NAME} --tail 200 || true
                            exit 1
                        fi

                        curl -fsS ${DOCKER_SMOKE_BASE_URL}/actuator/info >/dev/null
                        curl -fsS ${DOCKER_SMOKE_BASE_URL}/actuator/prometheus >/dev/null
                        echo "Docker smoke tests passed."
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes (Optional)') {
            when {
                expression { return params.DEPLOY_TO_K8S }
            }
            steps {
                script {
                    if (!params.KUBECONFIG_CREDENTIALS_ID?.trim()) {
                        error("DEPLOY_TO_K8S=true but KUBECONFIG_CREDENTIALS_ID is empty. Provide a Jenkins file credential containing kubeconfig.")
                    }

                    def k8sImageTag = params.K8S_IMAGE_TAG?.trim() ? params.K8S_IMAGE_TAG.trim() : env.BRANCH_NAME
                    def k8sImage = "${DOCKER_HUB_CREDENTIALS_USR}/${PROJECT_NAME}:${k8sImageTag}"

                    withCredentials([file(credentialsId: params.KUBECONFIG_CREDENTIALS_ID.trim(), variable: 'KUBECONFIG')]) {
                        sh """
                            set -e
                            kubectl version --client
                            kubectl config get-contexts
                            kubectl apply -n ${params.K8S_NAMESPACE} -f k8s/deployment.yaml
                            kubectl apply -n ${params.K8S_NAMESPACE} -f k8s/service.yaml
                            kubectl set image deployment/portfolio-app portfolio-app=${k8sImage} -n ${params.K8S_NAMESPACE}
                            kubectl rollout status deployment/portfolio-app -n ${params.K8S_NAMESPACE}
                            kubectl get pods -n ${params.K8S_NAMESPACE}
                            kubectl get svc portfolio-service -n ${params.K8S_NAMESPACE}
                        """
                    }
                }
            }
        }

        stage('Smoke Test - Kubernetes Deployment (Optional)') {
            when {
                expression { return params.DEPLOY_TO_K8S }
            }
            steps {
                script {
                    withCredentials([file(credentialsId: params.KUBECONFIG_CREDENTIALS_ID.trim(), variable: 'KUBECONFIG')]) {
                        sh '''
                            set -e

                            echo "Starting temporary port-forward for Kubernetes smoke tests..."
                            kubectl port-forward -n ${K8S_NAMESPACE} svc/portfolio-service 18081:8081 >/tmp/portfolio-port-forward.log 2>&1 &
                            PF_PID=$!

                            cleanup() {
                                kill $PF_PID >/dev/null 2>&1 || true
                            }
                            trap cleanup EXIT

                            for i in $(seq 1 30); do
                                if curl -fsS http://localhost:18081/actuator/health >/dev/null 2>&1; then
                                    echo "Kubernetes service is reachable."
                                    break
                                fi
                                if [ "$i" -eq 30 ]; then
                                    echo "ERROR: Kubernetes service did not become reachable via port-forward."
                                    cat /tmp/portfolio-port-forward.log || true
                                    kubectl get pods -n ${K8S_NAMESPACE}
                                    kubectl describe deployment/portfolio-app -n ${K8S_NAMESPACE} || true
                                    exit 1
                                fi
                                sleep 2
                            done

                            k8s_liveness=$(curl -fsS http://localhost:18081/actuator/health/liveness | grep -o '"status":"[A-Z]*"' | head -n1 | cut -d':' -f2 | tr -d '"')
                            k8s_readiness=$(curl -fsS http://localhost:18081/actuator/health/readiness | grep -o '"status":"[A-Z]*"' | head -n1 | cut -d':' -f2 | tr -d '"')

                            echo "K8s liveness status: ${k8s_liveness}"
                            echo "K8s readiness status: ${k8s_readiness}"

                            if [ "${k8s_liveness}" != "UP" ] || [ "${k8s_readiness}" != "UP" ]; then
                                echo "ERROR: Kubernetes smoke tests failed."
                                kubectl get pods -n ${K8S_NAMESPACE}
                                kubectl describe deployment/portfolio-app -n ${K8S_NAMESPACE} || true
                                exit 1
                            fi

                            curl -fsS http://localhost:18081/actuator/info >/dev/null
                            curl -fsS http://localhost:18081/actuator/prometheus >/dev/null
                            echo "Kubernetes smoke tests passed."
                        '''
                    }
                }
            }
        }
    }
}
