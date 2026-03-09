package com.example.portfolio.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contact Me");
        model.addAttribute("description", "Get in touch for collaboration opportunities");
        model.addAttribute("email", "kaan@example.com");
        model.addAttribute("github", "https://github.com/kaancorum");
        model.addAttribute("linkedin", "https://linkedin.com/in/kaancorum");
        
        return "contact";
    }
}
