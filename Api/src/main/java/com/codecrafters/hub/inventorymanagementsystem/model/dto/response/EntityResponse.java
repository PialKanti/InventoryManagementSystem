package com.codecrafters.hub.inventorymanagementsystem.model.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class EntityResponse {
    private Long id;
}
