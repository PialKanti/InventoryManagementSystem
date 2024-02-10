package com.codecrafters.hub.inventorymanagementsystem.dtos.response.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
