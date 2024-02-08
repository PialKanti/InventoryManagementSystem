package com.codecrafters.hub.inventorymanagementsystem.dtos.response.users;

import com.codecrafters.hub.inventorymanagementsystem.dtos.response.EntityResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UserResponse extends EntityResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
