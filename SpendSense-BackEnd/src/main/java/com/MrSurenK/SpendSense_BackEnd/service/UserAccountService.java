package com.MrSurenK.SpendSense_BackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;

@Service
public class UserAccountService {

	private UserAccountRepo userAccountRepo;
	private UserAccount userAccount;

	@Autowired
	private UserAccountService(UserAccountRepo userAccountRepo) {
		this.userAccountRepo = userAccountRepo;
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
		userAccount = new UserAccount();
		userAccount.setEmail(email);
		userAccount.setUsername(username);
		userAccount.setFirstName(userSignUpDto.getFirstName());
		userAccount.setLastName(userSignUpDto.getLastName());
		userAccount.setDateOfBirth(userSignUpDto.getDob());
		userAccount.setPassword(userSignUpDto.getPassword());

		userAccountRepo.save(userAccount); //Will throw runtime exceptions, no need to catch exceptions

	}

	//--- Form validations ---
	public boolean checkIfEmailExsits(String email) {
		return userAccountRepo.existsByEmail(email);
	}

	public boolean checkIfUsernameExists(String username) {
		return userAccountRepo.existsByUsername(username);
	}

}
