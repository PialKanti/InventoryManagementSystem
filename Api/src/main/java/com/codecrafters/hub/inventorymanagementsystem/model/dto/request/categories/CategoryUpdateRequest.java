package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateRequest(@NotBlank(message = "Name can not be blank")
                                    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters long")
                                    String name) {
}