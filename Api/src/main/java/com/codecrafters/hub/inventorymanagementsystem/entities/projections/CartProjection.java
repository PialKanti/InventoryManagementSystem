package com.codecrafters.hub.inventorymanagementsystem.entities.projections;

import java.util.List;

public interface CartProjection {
    String getUsername();
    List<CartItem> getCartItems();

    interface CartItem{
        ProductProjection getProduct();
        Integer getQuantity();
    }
}
