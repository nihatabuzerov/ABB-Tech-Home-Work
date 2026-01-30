package com.abbtech.exception.global;

import com.abbtech.exception.base.BaseErrorResponseDTO;
import com.abbtech.exception.base.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
       
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
       
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseErrorResponseDTO> handleBaseException(BaseException ex) {
        var error = ex.baseErrorService;
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(
                        BaseErrorResponseDTO.of(error.getErrorCode(),
                                                error.getMessage(),
                                                error.getHttpStatus())
                );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<BaseErrorResponseDTO> handleResponseStatus(ResponseStatusException ex) {
        int status = ex.getStatusCode().value();
        return ResponseEntity
                .status(status)
                .body(BaseErrorResponseDTO.of("RSE-" + status, ex.getReason(), status));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorResponseDTO> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseErrorResponseDTO.of("SERVER_ERROR", "Unexpected error", 500));
    }
}
