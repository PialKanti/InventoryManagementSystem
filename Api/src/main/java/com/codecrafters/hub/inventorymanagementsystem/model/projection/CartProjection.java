package com.codecrafters.hub.inventorymanagementsystem.model.projection;

import java.util.List;

public interface CartProjection {
    Long getId();
    String getUsername();
    List<CartItem> getCartItems();

    interface CartItem{
        ProductProjection getProduct();
        Integer getQuantity();
    }
}
