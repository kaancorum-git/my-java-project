package com.example.portfolio.controller.api;

import com.example.portfolio.model.dto.VersionResponse;
import com.example.portfolio.service.BuildInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VersionController {

    private final BuildInfoService buildInfoService;

    @Value("${spring.application.name:portfolio-service}")
    private String applicationName;

    @GetMapping("/version")
    public ResponseEntity<VersionResponse> version() {
        VersionResponse response = VersionResponse.builder()
                .applicationName(applicationName)
                .version("1.0.0")
                .buildNumber(buildInfoService.getBuildNumber())
                .branchName(buildInfoService.getBranchName())
                .uptimeSeconds(buildInfoService.getUptimeSeconds())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }
}
