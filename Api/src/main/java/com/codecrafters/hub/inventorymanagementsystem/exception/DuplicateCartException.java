package com.codecrafters.hub.inventorymanagementsystem.exception;

public class DuplicateCartException extends RuntimeException{
    public DuplicateCartException(String message) {
        super(message);
    }
}
