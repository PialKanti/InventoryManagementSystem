package com.codecrafters.hub.inventorymanagementsystem.dtos.request.products;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUpdateRequest {
    private Long id;
    private String title;
    private String description;
    private float price;
    private int quality;
}
