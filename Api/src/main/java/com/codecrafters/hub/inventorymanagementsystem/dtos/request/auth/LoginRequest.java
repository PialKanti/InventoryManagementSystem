package com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
