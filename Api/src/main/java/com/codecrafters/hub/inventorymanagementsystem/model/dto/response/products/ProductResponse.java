package com.codecrafters.hub.inventorymanagementsystem.model.dto.response.products;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.EntityResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ProductResponse extends EntityResponse {
    private String title;
    private String description;
    private float averageRating;
    private float price;
    private int quantity;
}
