package com.MrSurenK.SpendSense_BackEnd.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

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
