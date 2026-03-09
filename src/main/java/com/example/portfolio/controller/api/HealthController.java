package com.example.portfolio.controller.api;

import com.example.portfolio.model.dto.HealthResponse;
import com.example.portfolio.service.HealthCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthController {

    private final HealthCheckService healthCheckService;

    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        HealthResponse healthResponse = healthCheckService.getHealthStatus();
        return ResponseEntity.ok(healthResponse);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        return ResponseEntity.ok(Map.of(
            "application", "Portfolio Service",
            "description", "Spring Boot Portfolio Application",
            "timestamp", LocalDateTime.now(),
            "status", "running"
        ));
    }
}
