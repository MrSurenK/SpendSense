package com.MrSurenK.SpendSense_BackEnd.controller.controller_advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Handle Validation errors(DTO @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        //Checks for validation errors before throwing the exception
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(),error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    //Handle DB Constraint volations(eg, uniquem null, foreign key)
    @ExceptionHandler(DataIntegrityViolationException.class)
     public ResponseEntity<Map<String,String>> handleDataIntegrityViolation(DataIntegrityViolationException ex){
        Map<String, String> error = new HashMap<>();
        error.put("message", "A database error occured: " + ex.getMostSpecificCause());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
     }

     @ExceptionHandler(RuntimeException.class)
     public ResponseEntity<Object> handleRuntimeExceptions(RuntimeException ex){
        Map<String,Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message",ex.getMessage());
        error.put("status",HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);

     }


     /*Catch-all
     The HTTP 500 Internal Server Error response status code indicates that the server encountered an
     unexpected condition that prevented it from fulfilling the request.
     This error is a generic "catch-all" response to server issues,
     indicating that the server cannot find a more appropriate 5XX error to respond with.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericExceptions(Exception ex){
        Map<String, String> error = new HashMap<>();
        error.put("message","Something went wrong: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }





}
