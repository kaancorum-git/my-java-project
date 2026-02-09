# My Java Spring Project

This is a demo Spring Boot project showcasing a simple web application with Thymeleaf templates, Docker integration, and Jenkins pipeline automation.

## Features
- **Spring Boot**: Backend framework for building Java-based web applications.
- **Thymeleaf**: Templating engine for rendering dynamic HTML pages.
- **Docker**: Containerization for easy deployment.
- **Jenkins**: CI/CD pipeline for automated builds and deployments.

---

## Prerequisites
Make sure you have the following installed:
- **Java 17** or higher
- **Maven** (e.g., `/opt/homebrew/bin/mvn`)
- **Docker** (e.g., `/usr/local/bin/docker`)
- **Jenkins** (optional, for CI/CD)

---

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/my-java-project.git
cd my-java-project
```

### 2. Build the Project
Use Maven to build the project:
```bash
mvn clean package
```

### 3. Run the Application Locally
Run the Spring Boot application:
```bash
java -jar target/my-java-project-1.0.0.jar
```

Access the application at [http://localhost:8080](http://localhost:8080).

---

## Docker Integration

### 1. Build the Docker Image
```bash
docker build -t my-java-spring-project:local -f Dockerfile .
```

### 2. Run the Docker Container
```bash
docker run -d -p 80:80 --name my-java-spring-project my-java-spring-project:local
```

Access the application at [http://localhost](http://localhost).

---

## Jenkins Pipeline

### 1. Configure Jenkins
- Add the following environment variables in Jenkins:
  - `DOCKER_BIN`: Path to Docker binary (e.g., `/usr/local/bin/docker`)
  - `MAVEN_BIN`: Path to Maven binary (e.g., `/opt/homebrew/bin/mvn`)

### 2. Run the Pipeline
- Use the provided `Jenkinsfile` to automate the build, test, and deployment process.

---

## Endpoints

### 1. Home Page
- **URL**: `/`
- **Description**: Displays the home page with a link to the tutorial page.

### 2. Tutorial Page
- **URL**: `/tutorial`
- **Description**: Displays the tutorial page with dynamic content.

### 3. Favicon
- **URL**: `/favicon.ico`
- **Description**: Handles favicon requests with no content.

### 4. Error Page
- **URL**: `/error`
- **Description**: Displays a custom error message.

---

## Project Structure
```
src/
├── main/
│   ├── java/com/example/myjavaproject/
│   │   ├── MyJavaProjectApplication.java
│   │   ├── DefaultController.java
│   │   ├── TutorialController.java
│   │   ├── FaviconController.java
│   │   └── CustomErrorController.java
│   └── resources/
│       ├── templates/
│       │   ├── home.html
│       │   └── tutorial.html
│       └── application.properties
├── test/
│   └── java/com/example/myjavaproject/
Dockerfile
Jenkinsfile
pom.xml
```

---

## License
This project is licensed under the [MIT License](LICENSE).

---

## Author
**Kaan Corum**  
Feel free to reach out for any questions or contributions!