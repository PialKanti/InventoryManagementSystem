package com.codecrafters.hub.inventorymanagementsystem.entities.projections;

public interface ProductProjection extends BaseProjection {
    String getTitle();

    String getDescription();

    float getPrice();

    int getQuantity();
}
