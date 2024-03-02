package com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts;

import lombok.Data;

import java.util.List;

@Data
public class CartCreateRequest {
    private String username;
    private List<CartItem> cartItems;
}
