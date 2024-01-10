package com.codecrafters.hub.inventorymanagementsystem.entities.response;

import org.springframework.http.HttpStatus;

public record ErrorResponse(int status, String message, String description) {
    public static ErrorResponse build(HttpStatus httpStatus, String description) {
        return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), description);
    }
}
