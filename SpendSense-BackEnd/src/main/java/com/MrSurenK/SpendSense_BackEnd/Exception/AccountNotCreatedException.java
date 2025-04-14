package com.MrSurenK.SpendSense_BackEnd.Exception;

import lombok.Getter;

@Getter
public class AccountNotCreatedException extends RuntimeException{
    private final String EMAIL;
    private final String USERNAME;

    public AccountNotCreatedException(String message, String email, String username){
        super(message);
        this.EMAIL = email;
        this.USERNAME = username;
    }
//ToDo: Write an exception for it any of the fields are not accoridng to validation
}
