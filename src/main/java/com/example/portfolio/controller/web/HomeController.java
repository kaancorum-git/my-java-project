package com.example.portfolio.controller.web;

import com.example.portfolio.service.BuildInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BuildInfoService buildInfoService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Welcome to My Portfolio");
        model.addAttribute("description", "Professional Spring Boot Portfolio Application");
        model.addAttribute("buildNumber", buildInfoService.getBuildNumber());
        model.addAttribute("branchName", buildInfoService.getBranchName());
        model.addAttribute("uptime", buildInfoService.getUptimeSeconds());
        
        return "home";
    }
}
