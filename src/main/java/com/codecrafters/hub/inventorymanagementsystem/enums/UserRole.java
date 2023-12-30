package com.codecrafters.hub.inventorymanagementsystem.enums;

public enum UserRole {
    ADMIN("Admin"),
    USER("User");

    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    UserRole(String displayValue) {
        this.displayValue = displayValue;
    }
}
