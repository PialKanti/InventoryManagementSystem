package com.codecrafters.hub.inventorymanagementsystem.dtos.request.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequest {
    private Long id;
    private String firstName;
    private String lastName;
}
