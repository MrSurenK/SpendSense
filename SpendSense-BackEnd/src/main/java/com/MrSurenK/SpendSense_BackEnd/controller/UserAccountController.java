package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.LoginDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.RefreshRequest;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.LoginResponse;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import com.MrSurenK.SpendSense_BackEnd.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// To Do: Clean up global exceptions

@RestController
public class UserAccountController {

    UserAccountService userAccountService;

    UserSignUpDto userSignUpDto;

    private final JwtService jwtService;

    public UserAccountController(
                                 UserAccountService userAccountService,
                                 JwtService jwtService
                                 ){

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
    public ResponseEntity<String> newAccount(@Valid @RequestBody UserSignUpDto userSignUpDto){
        System.out.println(userSignUpDto);
        userAccountService.createAccount(userSignUpDto);
        return ResponseEntity.status(HttpStatus.OK).body("Account successfully created");
    }

    //Log user in
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto logindto){
        UserAccount authenticatedUser = userAccountService.authenticate(logindto);

        //if login was successful then generate refresh token and JWT access token
        String jwtToken = jwtService.generateToken(authenticatedUser);
        String refreshToken = jwtService.generateRefreshToken(logindto.getUsername());

        //Useful meta data for front end dev
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(jwtToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setAccessTokenExpiresIn(jwtService.getJwtExpiration());
        loginResponse.setRefreshTokenExpiresIn(jwtService.getRefreshExpiration());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/auth/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshRequest request){

        String username = jwtService.extractUsernameFromExpiredJWT(request.expiredAccessToken());

        if(jwtService.validateRefreshToken(username, request.refreshToken())){
            UserAccount userAccount = userAccountService.getUserAccount(username);
            String newAccessToken = jwtService.generateToken(userAccount);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(newAccessToken);
            loginResponse.setAccessTokenExpiresIn(jwtService.getJwtExpiration());
            loginResponse.setRefreshToken(request.refreshToken());
            loginResponse.setRefreshTokenExpiresIn(jwtService.getRefreshExpiration());

            return ResponseEntity.ok(loginResponse); //Successfully logged in again
        }
        return ResponseEntity.status(401).build(); //Refresh token is not valid, get user to log in again
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request){
//        //Get username from jwt claims
//        String username = jwtService.extractUsername(request.accessToken());
//        jwtService.deleteRefreshToken(request.accessToken());
//        return ResponseEntity.ok("Logged out successfully!");
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);

            //Blacklist access token in redis and delete refresh token
            jwtService.deleteRefreshTokenAndBlacklistAccessToken(token);

            return ResponseEntity.ok("Logged out successfully");

        }

        return ResponseEntity.badRequest().body("Authorization header missing or invalid");

    }

//    //For testing auth only: Remove endpoint before deploying!
//    @GetMapping("/members")
//    public ResponseEntity<List<UserAccount>>getUsers(){
//        List<UserAccount> users = userAccountService.allUsers();
//
//        return ResponseEntity.ok(users);
//    }
}
