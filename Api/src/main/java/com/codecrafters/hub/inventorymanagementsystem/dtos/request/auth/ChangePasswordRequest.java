package com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
