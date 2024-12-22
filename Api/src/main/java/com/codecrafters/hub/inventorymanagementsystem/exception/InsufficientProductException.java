package com.codecrafters.hub.inventorymanagementsystem.exception;

public class InsufficientProductException extends RuntimeException {
    public InsufficientProductException(String message) {
        super(message);
    }
}
