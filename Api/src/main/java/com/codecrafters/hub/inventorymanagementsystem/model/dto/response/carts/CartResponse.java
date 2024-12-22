package com.codecrafters.hub.inventorymanagementsystem.model.dto.response.carts;

import java.util.List;

public record CartResponse(Long id,
                           String username,
                           List<CartItemDto> cartItems) {
    public record CartItemDto(Long id,
                              CartProductDto product,
                              Integer quantity){
        public record CartProductDto(
                Long id,
                String title,
                Float price
        ) {}
    }
}