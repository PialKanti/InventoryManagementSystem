package com.codecrafters.hub.inventorymanagementsystem.enums;

public enum UserPermission {
    ADMIN_READ("Admin-Read"),
    ADMIN_CREATE("Admin-Create"),
    ADMIN_UPDATE("Admin-Update"),
    ADMIN_DELETE("Admin-Delete");

    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    UserPermission(String displayValue) {
        this.displayValue = displayValue;
    }
}
