package com.codecrafters.hub.inventorymanagementsystem.util;

import lombok.NoArgsConstructor;

import static com.codecrafters.hub.inventorymanagementsystem.util.StringUtils.isNullOrEmpty;

@NoArgsConstructor( access = lombok.AccessLevel.PRIVATE)
public class JwtUtils {
    public static String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (isNullOrEmpty(authorizationHeader)) {
            return "";
        }

        final String authHeaderPrefix = "Bearer ";
        return authorizationHeader.substring(authHeaderPrefix.length());
    }
}
