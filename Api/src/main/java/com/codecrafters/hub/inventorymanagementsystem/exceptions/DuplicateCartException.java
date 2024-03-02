package com.codecrafters.hub.inventorymanagementsystem.exceptions;

public class DuplicateCartException extends RuntimeException{
    public DuplicateCartException(String message) {
        super(message);
    }
}
