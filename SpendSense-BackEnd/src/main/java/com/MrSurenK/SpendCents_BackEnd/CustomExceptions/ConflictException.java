package com.MrSurenK.SpendCents_BackEnd.CustomExceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
