package com.codecrafters.hub.inventorymanagementsystem.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor( access = lombok.AccessLevel.PRIVATE)
public class StringUtils {
    public static boolean isNullOrEmpty(String input){
        return input == null || input.isEmpty();
    }
}
