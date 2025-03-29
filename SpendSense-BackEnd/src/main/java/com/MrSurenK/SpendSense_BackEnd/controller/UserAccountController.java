package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

    @Autowired
    public UserAccountController(UserAccountRepo userAccountRepo){
        this.userAccountRepo = userAccountRepo;
    }

    UserAccountRepo userAccountRepo;


    @PostMapping("/register")
    public ResponseEntity newAccount(@RequestBody UserSignUpDto userSignUpDto){
        return ResponseEntity.status(HttpStatus.OK).body("Account successfully created");
    }

}
