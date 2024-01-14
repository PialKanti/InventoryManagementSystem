package com.codecrafters.hub.inventorymanagementsystem.dtos.request.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
