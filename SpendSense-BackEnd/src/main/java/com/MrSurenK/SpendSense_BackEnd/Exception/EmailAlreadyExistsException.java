package com.MrSurenK.SpendSense_BackEnd.Exception;

import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends RuntimeException{
    private final String EMAIL;

    public EmailAlreadyExistsException(String message, String email){
        super(message);
        this.EMAIL = email;
    }

}
