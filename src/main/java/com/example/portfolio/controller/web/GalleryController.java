package com.example.portfolio.controller.web;

import com.example.portfolio.model.dto.PortfolioItem;
import com.example.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final PortfolioService portfolioService;

    @GetMapping("/gallery")
    public String gallery(Model model, @RequestParam(required = false) String category) {
        List<PortfolioItem> items;
        
        if (category != null && !category.isEmpty()) {
            items = portfolioService.getPortfolioItemsByCategory(category);
            model.addAttribute("selectedCategory", category);
        } else {
            items = portfolioService.getAllPortfolioItems();
            model.addAttribute("selectedCategory", "All");
        }
        
        model.addAttribute("title", "Portfolio Gallery");
        model.addAttribute("description", "Explore my projects and work");
        model.addAttribute("portfolioItems", items);
        
        return "gallery";
    }

    @GetMapping("/gallery/{id}")
    public String portfolioItemDetail(@PathVariable String id, Model model) {
        PortfolioItem item = portfolioService.getPortfolioItemById(id);
        
        if (item == null) {
            return "redirect:/gallery";
        }
        
        model.addAttribute("item", item);
        model.addAttribute("title", item.getTitle());
        
        return "portfolio-detail";
    }
}
