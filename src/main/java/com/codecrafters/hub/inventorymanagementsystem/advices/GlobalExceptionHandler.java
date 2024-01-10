package com.codecrafters.hub.inventorymanagementsystem.advices;

import com.codecrafters.hub.inventorymanagementsystem.entities.response.ErrorResponse;
import com.codecrafters.hub.inventorymanagementsystem.exceptions.PasswordMismatchException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.build(HttpStatus.UNAUTHORIZED, "Username or password is incorrect"));
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.build(HttpStatus.NOT_FOUND, "Entity not found"));
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.build(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(value = {PasswordMismatchException.class})
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(PasswordMismatchException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.build(HttpStatus.UNAUTHORIZED, exception.getMessage()));
    }
}
