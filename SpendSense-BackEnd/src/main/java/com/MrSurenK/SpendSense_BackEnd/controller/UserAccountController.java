package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.LoginDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ApiResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.LoginResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.UserSummary;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import com.MrSurenK.SpendSense_BackEnd.service.SecurityContextService;
import com.MrSurenK.SpendSense_BackEnd.service.UserAccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// To Do: Clean up global exceptions

@RestController
public class UserAccountController {

    UserAccountService userAccountService;

    UserSignUpDto userSignUpDto;

    private final JwtService jwtService;

    private final SecurityContextService securityContextService;

    public UserAccountController(
                                 UserAccountService userAccountService,
                                 JwtService jwtService,
                                 SecurityContextService securityContextService
                                 ){

        this.userAccountService = userAccountService;
        this.jwtService = jwtService;
        this.securityContextService = securityContextService;
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
    public ResponseEntity<Map<String,String>> newAccount(@Valid @RequestBody UserSignUpDto userSignUpDto){
        System.out.println(userSignUpDto);
        userAccountService.createAccount(userSignUpDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "Account successfully created"));
    }

    //Log user in
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto logindto, HttpServletResponse response){
        UserAccount authenticatedUser = userAccountService.authenticate(logindto);
        String userName = logindto.getUsername();

        //if login was successful then generate refresh token and JWT access token
        String jwtToken = jwtService.generateToken(authenticatedUser); //access token
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser);
        long accessTokenExpiration = jwtService.getJwtExpiration();
        long refreshTokenExpiration = jwtService.getRefreshExpiration();

        //Useful meta data for front end devsprin

        int userId = authenticatedUser.getId();
        String username = authenticatedUser.getUsername();
        LocalDateTime lastLogin = authenticatedUser.getLastLogin();

        LoginResponse loginResponse = new LoginResponse();

        //Store access token in HTTP Secure cookie as well
        Cookie accessTokenCookie = new Cookie("accessToken", jwtToken);
        accessTokenCookie.setHttpOnly(true); //prevents javascript access
        accessTokenCookie.setSecure(false); //Set true when deployed.. Only sent over HTTPS
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int)jwtService.getJwtExpiration()/1000); //Cap at 68yrs if overflow error
        accessTokenCookie.setAttribute("SameSite","Strict");//CSRF protection

        response.addCookie(accessTokenCookie);

        //Store Refresh token in HTTP Secure cookie
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true); //prevents javascript access
        refreshTokenCookie.setSecure(false); //Set true when deployed.. Only sent over HTTPS
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int)jwtService.getRefreshExpiration()/1000); //Cap at 68yrs if overflow error
        refreshTokenCookie.setAttribute("SameSite","Strict");//CSRF protection

        response.addCookie(refreshTokenCookie);

        //Return logged in user info metadata to be cached in state
        loginResponse.setUserId(userId);
        loginResponse.setUsername(username);
        loginResponse.setLastLogin(lastLogin);
        loginResponse.setMessage("User logged in successfully!");
        loginResponse.setAccessTokenExpiresIn(accessTokenExpiration);
        loginResponse.setRefreshTokenExpiresIn(refreshTokenExpiration);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/auth/refreshToken")
    public ResponseEntity<?> refreshToken(
                                          @CookieValue(name="refreshToken", required=true) String refreshToken,
                                          HttpServletResponse response){


        System.out.println(refreshToken);
        String username = jwtService.extractUsernameFromExpiredToken(refreshToken);

        if(jwtService.validateRefreshToken(username, refreshToken)){
            UserAccount userAccount = userAccountService.getUserAccount(username);
            String newAccessToken = jwtService.generateToken(userAccount);

            //Store access token in HTTP Secure cookie as well
            Cookie newAccessTokenCookie = new Cookie("accessToken", newAccessToken);
            newAccessTokenCookie.setHttpOnly(true); //prevents javascript access
            newAccessTokenCookie.setSecure(false); //Set true when deployed.. Only sent over HTTPS
            newAccessTokenCookie.setPath("/");
            newAccessTokenCookie.setMaxAge((int)jwtService.getJwtExpiration()/1000); //Cap at 68yrs if overflow error
            newAccessTokenCookie.setAttribute("SameSite","Strict");//CSRF protection

            response.addCookie(newAccessTokenCookie);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setMessage("JWT Access Token successfully refreshed!");
//            loginResponse.setAccessTokenExpiresIn(jwtService.getJwtExpiration());
//            loginResponse.setRefreshToken(request.refreshToken());
//            loginResponse.setRefreshTokenExpiresIn(jwtService.getRefreshExpiration());

            return ResponseEntity.ok(loginResponse); //Successfully logged in again
        }
        return ResponseEntity.status(401).build(); //Refresh token is not valid, get user to log in again
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(@CookieValue(name="accessToken", required=true) String accessToken){

        ApiResponse<Void> apiResponse = new ApiResponse();


        if(accessToken != null){
//            String token = authHeader.substring(7);

            //Blacklist access token in redis and delete refresh token
            jwtService.deleteRefreshTokenAndBlacklistAccessToken(accessToken);

            apiResponse.setSuccess(true);
            apiResponse.setMessage("Logged out successfully!");
            apiResponse.setData(null);
            return ResponseEntity.ok(apiResponse);
        }

        apiResponse.setSuccess(false);
        apiResponse.setMessage("No JWT token found in cookie.");
        return ResponseEntity.badRequest().body(apiResponse);

    }

    @GetMapping("/user/accountSummary")
    public ResponseEntity<ApiResponse<UserSummary>> getUserSummary(@RequestParam LocalDate date){

        int userId = securityContextService.getUserFromSecurityContext().getId();

        UserSummary accountSummary = userAccountService.userSummary(userId,date);

        ApiResponse<UserSummary> res = new ApiResponse<>();
        res.setSuccess(true);
        res.setMessage("User Summary successfully fetched");
        res.setData(accountSummary);

        return ResponseEntity.ok(res);
    }

//    //For testing auth only: Remove endpoint before deploying!
//    @GetMapping("/members")
//    public ResponseEntity<List<UserAccount>>getUsers(){
//        List<UserAccount> users = userAccountService.allUsers();
//
//        return ResponseEntity.ok(users);
//    }
}
