package com.f42o.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,ErrorResponse>> handleValid(MethodArgumentNotValidException ex) {
        Map<String, ErrorResponse> response = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> response
                        .put(error.getField(),ErrorResponse.builder()
                                .message(error.getDefaultMessage())
                                .status(HttpStatus.BAD_REQUEST.toString())
                                .timestamp(LocalDateTime.now())
                                .build()))
        ;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler({UserNotFoundException.class,BankAccountNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND.toString()).timestamp(LocalDateTime.now()).build());
    }
    @ExceptionHandler({InvalidAmountException.class,InsufficientFundsException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST.toString()).timestamp(LocalDateTime.now()).build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR.toString()).timestamp(LocalDateTime.now()).build());
    }
}
