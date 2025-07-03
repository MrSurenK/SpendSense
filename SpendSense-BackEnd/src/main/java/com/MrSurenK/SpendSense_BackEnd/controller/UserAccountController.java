package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.LoginDto;
import com.MrSurenK.SpendSense_BackEnd.dto.LoginResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import com.MrSurenK.SpendSense_BackEnd.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserAccountController {
    UserAccountRepo userAccountRepo;

    UserAccountService userAccountService;

    UserSignUpDto userSignUpDto;

    private final JwtService jwtService;

    public UserAccountController(UserAccountRepo userAccountRepo,
                                 UserAccountService userAccountService,
                                 JwtService jwtService
                                 ){
        this.userAccountRepo = userAccountRepo;
        this.userAccountService = userAccountService;
        this.jwtService = jwtService;
    }


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

    //Log user in
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto logindto){
        UserAccount authenticatedUser = userAccountService.authenticate(logindto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getJwtExpiration());

        return ResponseEntity.ok(loginResponse);
    }


    @GetMapping("/members")
    public ResponseEntity<List<UserAccount>>getUsers(){
        List<UserAccount> users = userAccountService.allUsers();

        return ResponseEntity.ok(users);
    }



}
