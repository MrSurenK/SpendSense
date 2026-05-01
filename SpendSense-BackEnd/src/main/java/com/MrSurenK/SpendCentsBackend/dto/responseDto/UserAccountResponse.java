package com.MrSurenK.SpendCentsBackend.dto.responseDto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserAccountResponse {

    private int id;
    private String email;
    private String username; // Must be unique in db
    private String firstName;
    private String lastName;
    private String occupation;
	private LocalDate dateOfBirth;
    private LocalDateTime lastLogin;
}
