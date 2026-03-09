# Portfolio Service (Spring Boot)

Production-like Spring Boot portfolio application with Thymeleaf UI, REST APIs, Docker image build/push, and Jenkins multibranch CI/CD deployment.

## Highlights
- Spring Boot 3.x, Java 17
- Thymeleaf-based portfolio UI with menu navigation
- Layered architecture: controller, service, model, config, exception
- Operational endpoints for health and version
- Docker-ready and Jenkins multibranch pipeline compatible
- Runs on port `8081`

## Tech Stack
- Spring Boot Web
- Thymeleaf
- Spring Boot Actuator
- Maven
- Docker
- Jenkins (multibranch pipeline)

## Prerequisites
- Java 17+
- Maven
- Docker
- Jenkins (optional for CI/CD)

## Run Locally
1. Build the application:

```bash
mvn clean package
```

2. Run the jar:

```bash
java -jar target/my-java-project-1.0.0.jar
```

3. Open:
- UI: http://localhost:8081/
- Health: http://localhost:8081/api/health
- Version: http://localhost:8081/api/version

## Docker
Build image:

```bash
docker build -t my-java-spring-boot-project:local -f Dockerfile .
```

Run container:

```bash
docker run -d -p 8081:8081 --name my-java-spring-boot-project my-java-spring-boot-project:local
```

Access:
- http://localhost:8081/

## Jenkins Pipeline
The Jenkinsfile supports multibranch workflow and does the following:
- Checkout branch source
- Build jar with Maven
- Build Docker image tagged by branch name
- Push image to Docker Hub
- Pull and run container mapped to `8081:8081`

## Endpoints
### Web
- `GET /` - Home page
- `GET /gallery` - Portfolio gallery (supports category filter via query param)
- `GET /about` - About page
- `GET /services` - Services page
- `GET /contact` - Contact page

### API
- `GET /api/health` - Application and system health details
- `GET /api/version` - Build/version/branch/uptime info
- `GET /api/info` - Service metadata
- `GET /api/portfolio` - Portfolio items
- `GET /api/portfolio/{id}` - Portfolio item detail
- `GET /api/portfolio/category/{category}` - Filter by category

### Actuator
- `GET /actuator/health`

## Project Structure
```text
src/main/java/com/example/portfolio/
  PortfolioApplication.java
  controller/
    api/
      HealthController.java
      VersionController.java
      PortfolioApiController.java
    web/
      HomeController.java
      GalleryController.java
      AboutController.java
      ServicesController.java
      ContactController.java
  service/
    BuildInfoService.java
    HealthCheckService.java
    PortfolioService.java
  model/
    dto/
      BuildInfoDTO.java
      HealthResponse.java
      VersionResponse.java
      PortfolioItem.java
    enums/
      HealthStatus.java
  config/
    WebConfig.java
  exception/
    GlobalExceptionHandler.java
    ResourceNotFoundException.java

src/main/resources/
  application.properties
  templates/
    home.html
    gallery.html
    portfolio-detail.html
    about.html
    services.html
    contact.html
  static/
    css/
      portfolio.css
    images/
```

## Notes
- Build metadata is read from `build-info.properties` (generated in Jenkins pipeline).
- Default configured port is `8081`.

## License
MIT