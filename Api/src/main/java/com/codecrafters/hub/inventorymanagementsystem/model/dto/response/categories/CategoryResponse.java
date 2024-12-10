package com.codecrafters.hub.inventorymanagementsystem.model.dto.response.categories;

import com.codecrafters.hub.inventorymanagementsystem.model.dto.response.EntityResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse extends EntityResponse {
    private String name;
}
