package com.example.portfolio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioItem {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String category;
    private List<String> technologies;
    private String projectUrl;
}
