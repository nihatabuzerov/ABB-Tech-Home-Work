package com.abb.task22.exception;

import com.abb.task22.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidationError(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        ErrorResponse error = new ErrorResponse(ErrorCode.VALIDATION_ERROR.name(), message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse>
    handleInsufficientBalance(InsufficientBalanceException e) {
        ErrorResponse error = new ErrorResponse(
                ErrorCode.INSUFFICIENT_BALANCE.name(),
                e.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleUserNotFound(UserNotFoundException e) {
        ErrorResponse error = new ErrorResponse(
                ErrorCode.USER_NOT_FOUND.name(),
                e.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handlePaymentNotFound(PaymentNotFoundException e) {
        ErrorResponse error = new ErrorResponse(
                ErrorCode.PAYMENT_NOT_FOUND.name(),
                e.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
