package com.codecrafters.hub.inventorymanagementsystem.model.enums;

import lombok.Getter;

@Getter
public enum UserPermission {
    ADMIN_READ("Admin-Read"),
    ADMIN_CREATE("Admin-Create"),
    ADMIN_UPDATE("Admin-Update"),
    ADMIN_DELETE("Admin-Delete");

    private final String displayValue;

    UserPermission(String displayValue) {
        this.displayValue = displayValue;
    }
}
