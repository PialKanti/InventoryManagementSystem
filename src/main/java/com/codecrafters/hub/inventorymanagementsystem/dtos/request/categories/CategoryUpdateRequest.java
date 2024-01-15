package com.codecrafters.hub.inventorymanagementsystem.dtos.request.categories;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryUpdateRequest {
    private String name;
}
