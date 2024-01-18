package com.codecrafters.hub.inventorymanagementsystem.dtos.request.users;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private Long id;
    private String firstName;
    private String lastName;
}
