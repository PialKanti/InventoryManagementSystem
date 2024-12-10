package com.codecrafters.hub.inventorymanagementsystem.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor( access = lombok.AccessLevel.PRIVATE)
public class JwtUtils {
    public static String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return "";
        }

        final String authHeaderPrefix = "Bearer ";
        return authorizationHeader.substring(authHeaderPrefix.length());
    }
}
