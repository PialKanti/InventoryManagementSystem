package com.codecrafters.hub.inventorymanagementsystem.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}