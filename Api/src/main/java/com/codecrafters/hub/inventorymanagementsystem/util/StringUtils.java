package com.codecrafters.hub.inventorymanagementsystem.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor( access = lombok.AccessLevel.PRIVATE)
public class StringUtils {
    /**
     * Checks if a given string is either {@code null} or empty.
     *
     * @param input the string to check
     * @return {@code true} if the string is {@code null} or its length is 0,
     *         {@code false} otherwise
     */
    public static boolean isNullOrEmpty(String input){
        return input == null || input.isEmpty();
    }
}
