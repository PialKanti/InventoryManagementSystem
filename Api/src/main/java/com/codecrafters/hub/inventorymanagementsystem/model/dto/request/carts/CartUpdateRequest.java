package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.carts;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartUpdateRequest {
    private List<CartItemDto> cartItems;
}
