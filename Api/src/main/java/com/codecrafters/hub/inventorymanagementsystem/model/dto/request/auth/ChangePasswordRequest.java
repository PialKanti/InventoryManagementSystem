package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth;

public record ChangePasswordRequest(String oldPassword,
                                    String newPassword) {
}