package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth;

import com.codecrafters.hub.inventorymanagementsystem.model.enums.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private List<UserRole> roles;
}
