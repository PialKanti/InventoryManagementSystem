package com.codecrafters.hub.inventorymanagementsystem.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstant {
    JWT_TOKEN_EXPIRED("JWT token expired"),
    USER_NOT_FOUND("User not found"),
    INVALID_OLD_PASSWORD("Invalid old password"),
    USER_ROLE_NOT_FOUND("User role not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    DUPLICATE_CART_EXCEPTION("Cart already exists for this user");

    private final String message;
}
