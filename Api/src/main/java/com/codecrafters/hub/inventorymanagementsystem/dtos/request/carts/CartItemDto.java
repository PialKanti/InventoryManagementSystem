package com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private long productId;
    private int quantity;
}
