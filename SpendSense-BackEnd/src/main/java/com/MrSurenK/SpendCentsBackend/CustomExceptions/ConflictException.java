package com.MrSurenK.SpendCentsBackend.CustomExceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
