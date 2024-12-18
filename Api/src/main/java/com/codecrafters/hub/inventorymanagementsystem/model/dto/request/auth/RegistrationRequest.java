package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.auth;

import com.codecrafters.hub.inventorymanagementsystem.model.enums.UserRole;

import java.util.List;

public record RegistrationRequest( String firstName,
                                   String lastName,
                                   String username,
                                   String email,
                                   String password,
                                   List<UserRole> roles) {
}