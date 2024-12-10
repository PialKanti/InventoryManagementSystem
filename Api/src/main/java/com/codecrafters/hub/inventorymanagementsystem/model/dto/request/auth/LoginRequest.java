package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
