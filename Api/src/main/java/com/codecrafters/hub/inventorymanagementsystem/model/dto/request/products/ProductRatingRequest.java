package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.products;

public record ProductRatingRequest(int rating,
                                   String comment,
                                   String username) {
}