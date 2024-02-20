package com.codecrafters.hub.inventorymanagementsystem.dtos.request.products;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String title;
    private String description;
    private float price;
    private int quantity;
}
