package com.codecrafters.hub.inventorymanagementsystem.advice;

import com.codecrafters.hub.inventorymanagementsystem.exception.InsufficientProductException;
import com.codecrafters.hub.inventorymanagementsystem.exception.PasswordMismatchException;
import com.codecrafters.hub.inventorymanagementsystem.exception.UnauthenticatedUserException;
import com.codecrafters.hub.inventorymanagementsystem.exception.ValidationError;
import com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.codecrafters.hub.inventorymanagementsystem.model.enums.ExceptionConstant.METHOD_ARGUMENT_NOT_VALID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException exception) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ValidationError> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError ->
                        ValidationError.builder()
                                .field(fieldError.getField())
                                .message(fieldError.getDefaultMessage())
                                .build())
                .toList();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, METHOD_ARGUMENT_NOT_VALID.getMessage());
        problemDetail.setProperty("errors", errors);

        return createResponseEntity(HttpStatus.BAD_REQUEST, problemDetail);
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
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        return createResponseEntity(status, problemDetail);
    }

    private ResponseEntity<ProblemDetail> createResponseEntity(HttpStatus status, ProblemDetail problemDetail) {
        return ResponseEntity.status(status).body(problemDetail);
    }
}
