package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserAccountController {

    @Autowired
    public UserAccountController(UserAccountRepo userAccountRepo,
                                 UserAccountService userAccountService
                                 ){
        this.userAccountRepo = userAccountRepo;
        this.userAccountService = userAccountService;
    }

    UserAccountRepo userAccountRepo;

    UserAccountService userAccountService;

    UserSignUpDto userSignUpDto;

    @PostMapping("/checkEmail")
    public ResponseEntity<Map<String,Object>> checkEmail(@RequestBody Map<String,String> request){
           String email =  request.get("email");
           boolean checkEmail = userAccountService.checkIfEmailExsits(email);
           //Custom response (un-ordered)
           return ResponseEntity.ok(
                   Map.of(
                           "exists",checkEmail,
                           "message", checkEmail ? "Email already exists" : "Email not in use"
                   )
           );
    }

    @PostMapping("/checkUsername")
    public ResponseEntity<Map<String,Object>> checkUsername(@RequestBody Map<String, String> request){
        String username = request.get("username");
        boolean exists = userAccountService.checkIfUsernameExists(username);

        Map<String, Object> json = new HashMap<>();
        json.put("exists", exists);
        json.put("message",exists ? "Username already take. Try another username" : "Username not in use");

        return ResponseEntity.ok(json);
    }

    //Throw 400 Bad Request if server not able to process request
    @PostMapping("/auth/register")
    public ResponseEntity newAccount(@Valid @RequestBody UserSignUpDto userSignUpDto){
        System.out.println(userSignUpDto);
        userAccountService.createAccount(userSignUpDto);
        return ResponseEntity.status(HttpStatus.OK).body("Account successfully created");
    }

}
