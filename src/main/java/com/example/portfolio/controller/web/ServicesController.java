package com.example.portfolio.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ServicesController {

    @GetMapping("/services")
    public String services(Model model) {
        List<Map<String, String>> servicesList = Arrays.asList(
            Map.of(
                "name", "Backend Development",
                "description", "Building robust Spring Boot microservices and RESTful APIs",
                "icon", "💻"
            ),
            Map.of(
                "name", "DevOps & CI/CD",
                "description", "Jenkins pipelines, Docker containerization, and Kubernetes orchestration",
                "icon", "🚀"
            ),
            Map.of(
                "name", "Cloud Architecture",
                "description", "Designing scalable cloud-native applications and infrastructure",
                "icon", "☁️"
            )
        );
        
        model.addAttribute("title", "My Services");
        model.addAttribute("description", "Professional services I offer");
        model.addAttribute("services", servicesList);
        
        return "services";
    }
}
