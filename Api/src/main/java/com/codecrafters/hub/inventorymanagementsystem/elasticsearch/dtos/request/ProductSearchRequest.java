package com.codecrafters.hub.inventorymanagementsystem.elasticsearch.dtos.request;

import lombok.Data;

@Data
public class ProductSearchRequest {
    private String title;
    private Long categoryId;
    private Float minPrice;
    private Float maxPrice;
}
