package com.codecrafters.hub.inventorymanagementsystem.entities.projections;

import java.math.BigDecimal;

public interface ProductProjection extends BaseProjection {
    String getTitle();

    String getDescription();

    Float getAverageRating();

    BigDecimal getPrice();

    Integer getQuantity();

    Category getCategory();

    interface Category{
        Long getId();
    }
}
