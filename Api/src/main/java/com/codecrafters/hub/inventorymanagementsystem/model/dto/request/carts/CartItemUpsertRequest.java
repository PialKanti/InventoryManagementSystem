package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts;

public record CartItemUpsertRequest(Long productId,
                                    int quantity) {
}
