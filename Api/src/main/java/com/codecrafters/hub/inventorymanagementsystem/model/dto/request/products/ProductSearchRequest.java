package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products;

import lombok.Data;

@Data
public class ProductSearchRequest {
    private String title;
    private Long categoryId;
    private Float minPrice;
    private Float maxPrice;
}
