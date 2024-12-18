package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts;

import java.util.List;

public record CartUpdateRequest(List<CartItemDto> cartItems) {
}