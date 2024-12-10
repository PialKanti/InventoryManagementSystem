package com.codecrafters.hub.inventorymanagementsystem.util;

public class JwtUtils {
    public static String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return "";
        }

        final String authHeaderPrefix = "Bearer ";
        return authorizationHeader.substring(authHeaderPrefix.length());
    }
}
