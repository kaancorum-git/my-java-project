package com.example.portfolio.controller.api;

import com.example.portfolio.model.dto.PortfolioItem;
import com.example.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioApiController {

    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<List<PortfolioItem>> getAllPortfolioItems() {
        return ResponseEntity.ok(portfolioService.getAllPortfolioItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioItem> getPortfolioItemById(@PathVariable String id) {
        PortfolioItem item = portfolioService.getPortfolioItemById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<PortfolioItem>> getPortfolioItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(portfolioService.getPortfolioItemsByCategory(category));
    }
}
