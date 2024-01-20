package com.codecrafters.hub.inventorymanagementsystem.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("Admin"),
    USER("User");

    private final String displayValue;

    UserRole(String displayValue) {
        this.displayValue = displayValue;
    }
}
