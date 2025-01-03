package com.codecrafters.hub.inventorymanagementsystem.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstant {
    JWT_TOKEN_EXPIRED("JWT token expired"),
    USER_NOT_FOUND("User not found"),
    UNAUTHENTICATED_USER_EXCEPTION("Authentication is required to access this resource"),
    INVALID_OLD_PASSWORD("Invalid old password"),
    METHOD_ARGUMENT_NOT_VALID("One or more fields do not match the validation parameters"),
    BAD_CREDENTIALS_EXCEPTION("Username or password is incorrect"),
    USER_ROLE_NOT_FOUND("User role not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    PRODUCT_NOT_FOUND("Product not found"),
    CART_NOT_FOUND("No cart not found"),
    CART_ITEM_NOT_FOUND("This item does not exist in cart"),
    DUPLICATE_CART_EXCEPTION("Cart already exists for this user"),
    INSUFFICIENT_PRODUCTS_EXCEPTION("Insufficient stock available in inventory");

    private final String message;
}
