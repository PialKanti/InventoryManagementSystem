package com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartCreateRequest {
    private String username;
    private List<CartItemDto> cartItems;
}
