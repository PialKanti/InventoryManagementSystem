package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.users;

import com.codecrafters.hub.inventorymanagementsystem.model.enums.UserRole;

import java.util.List;

public record UserUpdateRequest(Long id,
                                String firstName,
                                String lastName,
                                List<UserRole> roles) {
}