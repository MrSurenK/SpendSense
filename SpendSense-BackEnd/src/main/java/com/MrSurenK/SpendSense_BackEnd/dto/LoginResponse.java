package com.MrSurenK.SpendSense_BackEnd.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;

    private long accessTokenExpiresIn;

    private String refreshToken;

    private long refreshTokenExpiresIn;


}
