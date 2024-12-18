package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products;

public record ProductCreateRequest(String title,
                                   String description,
                                   float price,
                                   int quantity,
                                   Long categoryId) {
}