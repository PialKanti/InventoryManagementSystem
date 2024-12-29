package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(@NotBlank(message = "Old password can not be blank")
                                    String oldPassword,
                                    @NotBlank(message = "New password can not be blank")
                                    String newPassword) {
}