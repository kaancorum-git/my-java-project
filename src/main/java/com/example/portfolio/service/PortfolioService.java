package com.example.portfolio.service;

import com.example.portfolio.model.dto.PortfolioItem;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PortfolioService {

    public List<PortfolioItem> getAllPortfolioItems() {
        return Arrays.asList(
            PortfolioItem.builder()
                .id("1")
                .title("Spring Boot Microservice")
                .description("Production-ready Spring Boot application with Docker and Kubernetes deployment")
                .imageUrl("/images/placeholder-project1.jpg")
                .category("Backend")
                .technologies(Arrays.asList("Spring Boot", "Docker", "Jenkins", "Kubernetes"))
                .projectUrl("https://github.com/example/spring-microservice")
                .build(),
            
            PortfolioItem.builder()
                .id("2")
                .title("CI/CD Pipeline")
                .description("Automated Jenkins multibranch pipeline with Docker Hub integration")
                .imageUrl("/images/placeholder-project2.jpg")
                .category("DevOps")
                .technologies(Arrays.asList("Jenkins", "Docker", "Git", "Maven"))
                .projectUrl("https://github.com/example/cicd-pipeline")
                .build(),
            
            PortfolioItem.builder()
                .id("3")
                .title("Portfolio Website")
                .description("Modern portfolio website built with Spring Boot and Thymeleaf")
                .imageUrl("/images/placeholder-project3.jpg")
                .category("Full Stack")
                .technologies(Arrays.asList("Spring Boot", "Thymeleaf", "HTML/CSS", "JavaScript"))
                .projectUrl("https://github.com/example/portfolio")
                .build()
        );
    }

    public List<PortfolioItem> getPortfolioItemsByCategory(String category) {
        return getAllPortfolioItems().stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public PortfolioItem getPortfolioItemById(String id) {
        return getAllPortfolioItems().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
