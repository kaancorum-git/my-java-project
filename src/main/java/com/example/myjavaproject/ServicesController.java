package com.example.myjavaproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServicesController {

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("title", "Our Services");
        model.addAttribute("description", "Explore the services we offer to our users.");
        return "services";
    }
}
