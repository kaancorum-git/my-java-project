package com.example.myjavaproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TutorialController {

    @GetMapping("/tutorial")
    public String tutorial(Model model) {
        model.addAttribute("title", "Tutorial Page");
        model.addAttribute("content", "Welcome to the Tutorial Page! This is a simple demo.");
        return "tutorial"; // This maps to src/main/resources/templates/tutorial.html
    }
}
