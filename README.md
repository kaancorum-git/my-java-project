# My Java Project

This project is a simple Java application that demonstrates basic functionality and integration with CI/CD tools like Jenkins and GitHub Actions.

## Features
- Prints "Hello, World!" and "Hello World 2" to the console.
- Includes a `Jenkinsfile` for CI/CD automation with Jenkins.
- Configured with a GitHub Actions workflow for automated builds.

## Prerequisites
To run this project, you need:
- **Java 17** or higher installed.
- A Java compiler (`javac`).
- Git (optional, for cloning the repository).

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/kaancorum/my-java-project.git
cd my-java-project
```

### Compile and Run
1. Compile the Java code:
   ```bash
   javac -d out src/Main.java
   ```
2. Run the compiled program:
   ```bash
   java -cp out Main
   ```

## CI/CD Integration

### Jenkins
- The project includes a `Jenkinsfile` for automating builds and running the program.
- To use Jenkins:
  1. Add the repository to your Jenkins instance.
  2. Configure the pipeline to use the `Jenkinsfile`.

### GitHub Actions
- The project includes a GitHub Actions workflow (`.github/workflows/build.yml`) for automated builds.
- The workflow:
  - Checks out the code.
  - Sets up Java 17.
  - Builds and runs the program.

## License
This project is licensed under the [MIT License](LICENSE).

## Author
Created by **kaancorum**.