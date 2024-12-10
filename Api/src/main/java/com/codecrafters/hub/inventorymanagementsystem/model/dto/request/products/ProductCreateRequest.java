package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products;

import lombok.Data;

@Data
public class ProductCreateRequest {
    private String title;
    private String description;
    private float price;
    private int quantity;
    private Long categoryId;
}
