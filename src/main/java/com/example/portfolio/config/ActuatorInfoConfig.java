package com.example.portfolio.config;

import com.example.portfolio.service.BuildInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ActuatorInfoConfig {

    @Bean
    public InfoContributor buildInfoContributor(
            BuildInfoService buildInfoService,
            @Value("${info.app.version:1.0.0}") String appVersion) {
        return (Info.Builder builder) -> builder.withDetail("build", Map.of(
                "number", buildInfoService.getBuildNumber(),
                "branch", buildInfoService.getBranchName(),
                "version", appVersion,
                "uptimeSeconds", buildInfoService.getUptimeSeconds()
        ));
    }
}
