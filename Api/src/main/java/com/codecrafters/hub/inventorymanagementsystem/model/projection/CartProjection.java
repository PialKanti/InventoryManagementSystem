package com.codecrafters.hub.inventorymanagementsystem.model.projection;

import java.util.List;

public interface CartProjection {
    Long getId();
    String getUsername();
    List<CartItem> getCartItems();

    interface CartItem{
        Long getId();
        ProductProjection getProduct();
        Integer getQuantity();
    }

    interface ProductProjection{
        Long getId();
        String getTitle();
        Float getPrice();
    }
}
