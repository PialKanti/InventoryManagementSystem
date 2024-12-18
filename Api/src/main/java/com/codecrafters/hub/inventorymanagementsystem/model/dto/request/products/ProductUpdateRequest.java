package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products;

public record ProductUpdateRequest(Long id,
                                   String title,
                                   String description,
                                   float price,
                                   int quantity) {
}