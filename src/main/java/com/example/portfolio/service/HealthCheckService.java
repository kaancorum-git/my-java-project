package com.example.portfolio.service;

import com.example.portfolio.model.dto.HealthResponse;
import com.example.portfolio.model.enums.HealthStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HealthCheckService {

    private final BuildInfoService buildInfoService;

    public HealthResponse getHealthStatus() {
        Map<String, Object> details = new HashMap<>();
        
        // Check disk space
        File root = new File("/");
        long totalSpace = root.getTotalSpace();
        long freeSpace = root.getFreeSpace();
        double freePercentage = (double) freeSpace / totalSpace * 100;
        
        details.put("diskSpace", Map.of(
            "total", formatBytes(totalSpace),
            "free", formatBytes(freeSpace),
            "freePercentage", String.format("%.2f%%", freePercentage)
        ));
        
        // Add memory info
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        details.put("memory", Map.of(
            "max", formatBytes(maxMemory),
            "total", formatBytes(totalMemory),
            "used", formatBytes(usedMemory),
            "free", formatBytes(freeMemory)
        ));
        
        // Add build info
        details.put("build", Map.of(
            "number", buildInfoService.getBuildNumber(),
            "branch", buildInfoService.getBranchName(),
            "uptime", buildInfoService.getUptimeSeconds() + "s"
        ));
        
        // Determine overall status
        HealthStatus status = HealthStatus.UP;
        String message = "Application is running normally";
        
        if (freePercentage < 10) {
            status = HealthStatus.DEGRADED;
            message = "Low disk space warning";
        }
        
        return HealthResponse.builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .details(details)
                .build();
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.2f %sB", bytes / Math.pow(1024, exp), pre);
    }
}
