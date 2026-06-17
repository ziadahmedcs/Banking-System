package com.banking.system.banking_system.GlobalExeception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(
            ResponseStatusException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", ex.getStatusCode().value());
        response.put("message", ex.getReason());

        return new ResponseEntity<>(
                response,
                ex.getStatusCode()
        );
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> UsernameNotFoundException (ResponseStatusException ex)
    {
        Map <String ,Object> response = new HashMap<>() ;
        response.put("message" , ex.getMessage()) ;
        response.put("status" , ex.getStatusCode().value()) ;
        return new ResponseEntity<>(response , ex.getStatusCode()) ;
    }
    @ExceptionHandler(BadCredentialsException .class)
    public ResponseEntity<?> handleBadCredentials(
            BadCredentialsException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", 401);
        response.put("message", "Invalid Email or Password");

        return ResponseEntity.status(401).body(response);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map> handleEnumError(
            HttpMessageNotReadableException ex) {
        Map<String,Object>response = new HashMap<>() ;
        response.put("message" ,"Account Type must be SAVINGS or CURRENT") ;
        response.put("status",HttpStatus.BAD_REQUEST) ;
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}