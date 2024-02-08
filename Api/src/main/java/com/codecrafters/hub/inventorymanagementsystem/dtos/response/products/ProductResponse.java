package com.codecrafters.hub.inventorymanagementsystem.dtos.response.products;

import com.codecrafters.hub.inventorymanagementsystem.dtos.response.EntityResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ProductResponse extends EntityResponse {
    private String title;
    private String description;
    private float price;
    private int quantity;
}
