# My Java Project

This project is a simple Java application that demonstrates basic functionality and integration with CI/CD tools like Jenkins and GitHub Actions. It also includes an Nginx server running in a Docker container to serve static files.

## Features
- Prints "Hello, World!" to the console.
- Checks if the Nginx-served page is accessible and prints its content.
- Includes a `Jenkinsfile` for CI/CD automation with Jenkins.
- Configured with GitHub Actions workflows for:
  - Automated builds (`build.yml`).
  - Versioning and tagging (`versioning.yml`).
- Serves static files using Nginx in a Docker container.

## Prerequisites
To run this project, you need:
- **Java 17** or higher installed.
- A Java compiler (`javac`).
- Docker installed and running.
- Git (optional, for cloning the repository).

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/kaancorum/my-java-project.git
cd my-java-project
```

### Compile and Run the Java Application
1. Compile the Java code:
   ```bash
   javac -d out src/Main.java
   ```
2. Run the compiled program:
   ```bash
   java -cp out Main
   ```

### Build and Run the Docker Container
1. Build the Docker image:
   ```bash
   docker build -t my-java-nginx-project:latest .
   ```
2. Run the Docker container:
   ```bash
   docker run -d -p 80:80 --name my-java-nginx-project my-java-nginx-project:latest
   ```
3. Access the Nginx server:
   - Open a browser and go to `http://localhost/`.
   - You should see the content of the `index.html` file served by Nginx.

### Debugging
- To inspect the running container:
  ```bash
  docker exec -it my-java-nginx-project sh
  ```
- To check the logs:
  ```bash
  docker logs my-java-nginx-project
  ```

## CI/CD Integration

### Jenkins
- The project includes a `Jenkinsfile` for automating builds and running the program.
- Key stages in the Jenkins pipeline:
  - **Info**: Gathers system information and prints environment details.
  - **Build Docker Image**: Builds the Docker image for the Nginx server.
  - **Run Docker Container**: Stops any existing container on port `80` and runs a new one.
  - **Build Java Project**: Compiles the Java application.
  - **Run Java Project**: Executes the compiled Java program.

### GitHub Actions
- The project includes multiple GitHub Actions workflows:
  - **Build Workflow (`build.yml`)**:
    - Checks out the code.
    - Sets up Java 17.
    - Builds and runs the Java program.
  - **Versioning Workflow (`versioning.yml`)**:
    - Automatically creates and pushes tags based on branch and event type.

## License
This project is licensed under the [MIT License](LICENSE).

## Author
Created by **kaancorum**.