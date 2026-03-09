package com.example.portfolio.model.dto;

import com.example.portfolio.model.enums.HealthStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponse {
    private HealthStatus status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, Object> details;
}
