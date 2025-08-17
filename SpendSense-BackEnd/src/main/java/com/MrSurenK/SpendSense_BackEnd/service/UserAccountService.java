package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.LoginDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.UserSummary;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountService {

	private final UserAccountRepo userAccountRepo;
    private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;


	private UserAccountService(UserAccountRepo userAccountRepo, PasswordEncoder passwordEncoder,
							   AuthenticationManager authenticationManager) {
		this.userAccountRepo = userAccountRepo;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public void createAccount(UserSignUpDto userSignUpDto) {

		String email = userSignUpDto.getEmail();
		String username = userSignUpDto.getUserName();

		if(checkIfEmailExsits(email)){
			throw new RuntimeException("Email already exists: " + email);
		}

		if(checkIfUsernameExists(username)){
			throw new RuntimeException("Username already exists: " + username);
		}

		// Map fields to user account
        UserAccount userAccount = new UserAccount();
		userAccount.setEmail(email);
		userAccount.setUsername(username);
		userAccount.setFirstName(userSignUpDto.getFirstName());
		userAccount.setLastName(userSignUpDto.getLastName());
		userAccount.setDateOfBirth(userSignUpDto.getDob());
		userAccount.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));

		userAccountRepo.save(userAccount); //Will throw runtime exceptions, no need to catch exceptions

	}

	//--- Form validations --- //
	public boolean checkIfEmailExsits(String email) {
		return userAccountRepo.existsByEmail(email);
	}

	public boolean checkIfUsernameExists(String username) {
		return userAccountRepo.existsByUsername(username);
	}

	//Authenticate user
	public UserAccount authenticate(LoginDto input){
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						input.getUsername(),
						input.getPassword()
				)
		);

		UserAccount user = userAccountRepo.findByUsername(input.getUsername()).orElseThrow();

		user.setLastLogin(LocalDateTime.now()); //Update the last login in Db

		userAccountRepo.save(user);

		return user;
	}

	//Generic method to get user account outside of login
	public UserAccount getUserAccount(String username){
		return userAccountRepo.findByUsername(username).orElseThrow();
	}

	//Get all user service (For testing JWT protected endpoint)
	public List<UserAccount> allUsers(){

        List<UserAccount> users = new ArrayList<>(userAccountRepo.findAll());
		return users;
	}

	//ToDO: UserAccount summary
	/*
	* Name
	* Age
	* Occupation
	* Income
	*/
	public UserSummary userSummary(int userId, LocalDate currYear){

		UserAccount user = userAccountRepo.findById(userId).orElseThrow(()
				-> new EntityNotFoundException("User not found"));

		UserSummary userSummary = new UserSummary();
		userSummary.setId(user.getId());
		userSummary.setFirstName(user.getFirstName());
		userSummary.setLastName(user.getLastName());
		userSummary.setLastLogin(user.getLastLogin());
		LocalDate dob = user.getDateOfBirth();
		Period period = Period.between(dob, currYear);
		int age = period.getYears();
		userSummary.setAge(age);
		userSummary.setOccupation(user.getOccupation());

		return userSummary;
	}
}
