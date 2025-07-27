package com.MrSurenK.SpendSense_BackEnd.dto.requestDto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignUpDto {

	@Email(regexp = "^(?!.*\\.\\.)[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$", message = "Please input a valid email")
	@NotBlank(message = "Please provide a valid email")
	private String email;

	@NotBlank(message = "Please provide a username")
	private String userName;

	@NotBlank(message = "Please provide a first name")
	private String firstName;

	@NotBlank(message = "Please provide a last name")
	private String lastName;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dob;

	@NotBlank(message = "Password Required")
	@Size(min = 8, message = "Password should be at least 8 characters long")
	private String password;
}
