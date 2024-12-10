package com.codecrafters.hub.inventorymanagementsystem.model.dto.request.users;

import com.codecrafters.hub.inventorymanagementsystem.model.enums.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private List<UserRole> roles;
}
