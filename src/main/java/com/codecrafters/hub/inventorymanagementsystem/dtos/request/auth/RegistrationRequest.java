package com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth;

import com.codecrafters.hub.inventorymanagementsystem.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private List<UserRole> roles;
}
