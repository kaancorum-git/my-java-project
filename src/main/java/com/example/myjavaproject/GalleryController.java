package com.example.myjavaproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GalleryController {

    @GetMapping("/gallery")
    public String gallery(Model model) {
        model.addAttribute("title", "Gallery");
        model.addAttribute("description", "Check out our gallery below.");
        return "gallery";
    }
}
