package com.codecrafters.hub.inventorymanagementsystem.utils;

public class JwtUtils {
    public static String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return "";
        }

        final String authHeaderPrefix = "Bearer ";
        return authorizationHeader.substring(authHeaderPrefix.length());
    }
}
