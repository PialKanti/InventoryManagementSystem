package com.codecrafters.hub.inventorymanagementsystem.dtos.request.carts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private long productId;
    private int quantity;
}
