package com.codecrafters.hub.inventorymanagementsystem.advice;

import com.codecrafters.hub.inventorymanagementsystem.exception.InsufficientProductException;
import com.codecrafters.hub.inventorymanagementsystem.exception.PasswordMismatchException;
import com.codecrafters.hub.inventorymanagementsystem.exception.UnauthenticatedUserException;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException exception) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException exception) {
        return createResponseEntity(HttpStatus.UNAUTHORIZED, ExceptionConstant.BAD_CREDENTIALS_EXCEPTION.getMessage());
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException exception) {
        return createResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return createResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(value = {PasswordMismatchException.class})
    public ResponseEntity<ProblemDetail> handlePasswordMismatchException(PasswordMismatchException exception) {
        return createResponseEntity(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(value = {UnauthenticatedUserException.class})
    public ResponseEntity<ProblemDetail> handleUnauthenticatedUserException(UnauthenticatedUserException exception) {
        return createResponseEntity(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(value = {InsufficientProductException.class})
    public ResponseEntity<ProblemDetail> handleInsufficientProductException(InsufficientProductException exception) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<ProblemDetail> createResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, message));
    }
}
