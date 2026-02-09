package com.example.myjavaproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Controller
public class DefaultController {

    @Value("${build.info.file}")
    private String buildInfoFile;

    @GetMapping("/")
    public String home(Model model) throws IOException {
        Properties buildInfo = new Properties();
        buildInfo.load(Files.newInputStream(Paths.get(buildInfoFile)));

        String buildNumber = buildInfo.getProperty("build.number", "N/A");
        String branchName = buildInfo.getProperty("branch.name", "N/A");

        model.addAttribute("title", "Welcome to My Java Project!");
        model.addAttribute("description", "This is a demo page created with Spring Boot and Thymeleaf.");
        model.addAttribute("buildNumber", buildNumber);
        model.addAttribute("branchName", branchName);

        return "home"; // This maps to src/main/resources/templates/home.html
    }
}
