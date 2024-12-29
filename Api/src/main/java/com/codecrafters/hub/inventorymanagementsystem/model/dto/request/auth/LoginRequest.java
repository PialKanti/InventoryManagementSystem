package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotBlank(message = "Username can not be blank")
                           @Size(min = 3, max = 35, message = "Username should be between 3 and 10 characters long")
                           String username,
                           @NotBlank(message = "Password can not be blank")
                           String password) {
}