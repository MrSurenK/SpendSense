package com.MrSurenK.SpendCentsBackend.dto.responseDto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserSummary {

    private Integer id;

    private String firstName;

    private String lastName;

    private String occupation;

    private int age;
    
    private LocalDateTime lastLogin;
}
