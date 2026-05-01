package com.MrSurenK.SpendCentsBackend.dto.responseDto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponse {

    private int userId;

    private String username;

    private LocalDateTime lastLogin;

    private String message;

    private long accessTokenExpiresIn;

    private long refreshTokenExpiresIn;
}
