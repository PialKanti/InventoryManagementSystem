package com.codecrafters.hub.inventorymanagementsystem.dtos.response.categories;

import com.codecrafters.hub.inventorymanagementsystem.dtos.response.EntityResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CategoryResponse extends EntityResponse {
    private String name;
}
