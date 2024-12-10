package com.codecrafters.hub.inventorymanagementsystem.model.entity.projection;

import java.util.List;

public interface CartProjection {
    String getUsername();
    List<CartItem> getCartItems();

    interface CartItem{
        ProductProjection getProduct();
        Integer getQuantity();
    }
}
