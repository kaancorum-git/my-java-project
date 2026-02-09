package com.example.myjavaproject;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "An error occurred. Please check your request.";
    }

    // This method is deprecated in Spring Boot 2.3+, but it's required for older versions.
    public String getErrorPath() {
        return "/error";
    }
}
