package com.example.myjavaproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Welcome to My Java Project!");
        model.addAttribute("description", "This is a demo page created with Spring Boot and Thymeleaf.");
        return "home"; // This maps to src/main/resources/templates/home.html
    }
}
