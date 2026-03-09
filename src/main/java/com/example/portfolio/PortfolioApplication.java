package com.example.portfolio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class PortfolioApplication {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PortfolioApplication.class, args);
        
        String appName = context.getEnvironment().getProperty("spring.application.name");
        String port = context.getEnvironment().getProperty("server.port");
        
        log.info("========================================");
        log.info("Application: {} started successfully!", appName);
        log.info("Access URLs:");
        log.info("  Local:      http://localhost:{}", port);
        log.info("  Web UI:     http://localhost:{}/", port);
        log.info("  Health:     http://localhost:{}/api/health", port);
        log.info("  Version:    http://localhost:{}/api/version", port);
        log.info("  Actuator:   http://localhost:{}/actuator/health", port);
        log.info("========================================");
    }
}
