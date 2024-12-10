package com.codecrafters.hub.inventorymanagementsystem.model.dto.response.users;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
