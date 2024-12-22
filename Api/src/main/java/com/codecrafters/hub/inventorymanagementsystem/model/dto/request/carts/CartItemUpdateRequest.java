package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts;

public record CartItemUpdateRequest(Long productId,
                                    int quantity) {
}
