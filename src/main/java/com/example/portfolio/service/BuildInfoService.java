package com.example.portfolio.service;

import com.example.portfolio.model.dto.BuildInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Properties;

@Slf4j
@Service
public class BuildInfoService {

    @Value("${build.info.file}")
    private String buildInfoFile;

    @Value("${spring.application.name:portfolio-service}")
    private String applicationName;

    private Properties buildInfo;
    private final Instant startTime = Instant.now();

    @PostConstruct
    public void init() {
        loadBuildInfo();
    }

    private void loadBuildInfo() {
        try {
            buildInfo = new Properties();
            buildInfo.load(Files.newInputStream(Paths.get(buildInfoFile)));
            log.info("Build info loaded successfully: Build #{}, Branch: {}", 
                getBuildNumber(), getBranchName());
        } catch (IOException e) {
            log.warn("Failed to load build info from {}: {}", buildInfoFile, e.getMessage());
            buildInfo = new Properties();
            buildInfo.setProperty("build.number", "N/A");
            buildInfo.setProperty("branch.name", "N/A");
        }
    }

    public String getBuildNumber() {
        return buildInfo.getProperty("build.number", "N/A");
    }

    public String getBranchName() {
        return buildInfo.getProperty("branch.name", "N/A");
    }

    public Long getUptimeSeconds() {
        return Instant.now().getEpochSecond() - startTime.getEpochSecond();
    }

    public BuildInfoDTO getBuildInfoDTO() {
        return BuildInfoDTO.builder()
                .buildNumber(getBuildNumber())
                .branchName(getBranchName())
                .version("1.0.0")
                .uptimeSeconds(getUptimeSeconds())
                .build();
    }
}
