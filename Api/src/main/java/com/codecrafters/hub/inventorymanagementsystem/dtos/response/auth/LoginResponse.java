package com.codecrafters.hub.inventorymanagementsystem.dtos.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
