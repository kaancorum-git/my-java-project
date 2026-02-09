package com.example.myjavaproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconController {

    @GetMapping("/favicon.ico")
    public void handleFavicon() {
        // Return no content for favicon requests
    }
}
