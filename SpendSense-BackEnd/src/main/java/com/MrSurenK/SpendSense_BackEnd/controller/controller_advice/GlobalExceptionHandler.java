package com.MrSurenK.SpendSense_BackEnd.controller.controller_advice;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //ToDo: Refactor http exceptions to be clearner and more specofic. Only use Exception.class for catch-all

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

     /*
      Do not use generic runtime exceptoons for HTTP error responses as it overwrites the real HTTP status codes and
      sends 409
      */

//     @ExceptionHandler(RuntimeException.class)
//     public ResponseEntity<Object> handleRuntimeExceptions(RuntimeException ex){
//        Map<String,Object> error = new HashMap<>();
//        error.put("timestamp", LocalDateTime.now());
//        error.put("message",ex.getMessage());
//        error.put("status",HttpStatus.CONFLICT.value());
//
//        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
//
//     }

     //Spring Security Authentication and Authorisation HTTP errors
     @ExceptionHandler(Exception.class)
     public ProblemDetail handleSecurityException(Exception exception){
        ProblemDetail errorDetail = null; //ToDo: Set a URI type for ProblemDetail Responses to be consistent with RFC

        exception.printStackTrace();

        if(exception instanceof BadCredentialsException){
            //HTTP Unauthorised
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),exception.getMessage());
            errorDetail.setProperty("description","The username or password is incorrect!");
        }

        //HTTP Bad Forbidden
        else if(exception instanceof AccountStatusException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),exception.getMessage());
            errorDetail.setProperty("description","The account is locked!");
        }

        else if(exception instanceof AccessDeniedException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),exception.getMessage());
            errorDetail.setProperty("descrption","You are not authorized to access this account");
        }

         else if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        }

        else if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }

        /*
     Catch-all
     The HTTP 500 Internal Server Error response status code indicates that the server encountered an
     unexpected condition that prevented it from fulfilling the request.
     This error is a generic "catch-all" response to server issues,
     indicating that the server cannot find a more appropriate 5XX error to respond with.
     */
        else if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }

        return errorDetail;

     }



//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleGenericExceptions(Exception ex){
//        Map<String, String> error = new HashMap<>();
//        error.put("message","Something went wrong: " + ex.getMessage());
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }





}
