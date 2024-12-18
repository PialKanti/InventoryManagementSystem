package com.codecrafters.hub.inventorymanagementsystem.advice;

import com.codecrafters.hub.inventorymanagementsystem.exception.PasswordMismatchException;
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
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException exception) {
        return createResponseEntity(HttpStatus.UNAUTHORIZED, "Username or password is incorrect");
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

    private ResponseEntity<ProblemDetail> createResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, message));
    }
}
