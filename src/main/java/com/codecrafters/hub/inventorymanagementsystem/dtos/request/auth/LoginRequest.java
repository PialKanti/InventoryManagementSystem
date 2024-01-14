package com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
