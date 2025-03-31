package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

    @Autowired
    public UserAccountController(UserAccountRepo userAccountRepo,
                                 UserAccountService userAccountService){
        this.userAccountRepo = userAccountRepo;
        this.userAccountService = userAccountService;
    }

    UserAccountRepo userAccountRepo;

    UserAccountService userAccountService;


    @PostMapping("/register")
    public ResponseEntity newAccount(@RequestBody UserSignUpDto userSignUpDto){
        System.out.println(userSignUpDto);
        userAccountService.createAccount(userSignUpDto);
        return ResponseEntity.status(HttpStatus.OK).body("Account successfully created");
    }

    //Todo: Handle error handling effectively
    //ToDo: Do not return Account successfully created just because API reponse 200 O.K as it might not be
}
