package com.codecrafters.hub.inventorymanagementsystem.dtos.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class EntityResponse {
    private Long id;
}
