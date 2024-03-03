package com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts;

import lombok.Data;

import java.util.List;

@Data
public class CartUpdateRequest {
    private List<CartItemDto> cartItems;
}
