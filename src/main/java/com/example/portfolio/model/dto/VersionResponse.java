package com.example.portfolio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionResponse {
    private String applicationName;
    private String version;
    private String buildNumber;
    private String branchName;
    private Long uptimeSeconds;
    private LocalDateTime timestamp;
}
