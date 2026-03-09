package com.example.portfolio.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About Me");
        model.addAttribute("description", "Learn more about my skills and experience");
        model.addAttribute("name", "Kaan Corum");
        model.addAttribute("role", "Software Engineer");
        model.addAttribute("bio", "Passionate software engineer specializing in Java, Spring Boot, and cloud-native applications.");
        
        return "about";
    }
}
