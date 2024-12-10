package com.codecrafters.hub.inventorymanagementsystem.model.dto.response.products;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.EntityResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse extends EntityResponse {
    private String title;
    private String description;
    private float averageRating;
    private float price;
    private int quantity;
}
