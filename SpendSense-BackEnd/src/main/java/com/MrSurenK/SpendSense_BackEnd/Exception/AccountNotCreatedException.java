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


    public static AccountNotCreatedException emailError(String message, String email){
        return new AccountNotCreatedException(message, email, null);
    }


    public static AccountNotCreatedException userNameError(String message, String username){
        return new AccountNotCreatedException(message, null, username);
    }
//ToDo: Write an exception for it any of the fields are not accoridng to validation
}
