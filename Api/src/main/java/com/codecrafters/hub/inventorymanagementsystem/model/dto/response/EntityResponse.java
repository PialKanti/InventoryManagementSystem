package com.codecrafters.hub.inventorymanagementsystem.model.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class EntityResponse {
    private Long id;
}
