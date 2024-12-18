package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts;

import java.util.List;

public record CartCreateRequest(String username,
                                List<CartItemDto> cartItems) {
}