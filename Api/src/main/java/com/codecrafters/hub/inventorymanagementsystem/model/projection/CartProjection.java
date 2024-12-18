package com.codecrafters.hub.inventorymanagementsystem.model.projection;

import java.util.List;

public interface CartProjection {
    String getUsername();
    List<CartItem> getCartItems();

    interface CartItem{
        ProductProjection getProduct();
        Integer getQuantity();
    }
}
