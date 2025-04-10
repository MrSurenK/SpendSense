package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.Exception.EmailAlreadyExistsException;
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

//	public void createAccount(UserSignUpDto userSignUpDto) {
//		if (!checkIfAccountExsits(userSignUpDto)) {
//			// Map fields to user account
//			userAccount = new UserAccount();
//			userAccount.setEmail(userSignUpDto.getEmail());
//			userAccount.setUserName(userSignUpDto.getUserName());
//			userAccount.setFirstName(userSignUpDto.getFirstName());
//			userAccount.setLastName(userSignUpDto.getLastName());
//			userAccount.setDateOfBirth(userSignUpDto.getDob());
//			userAccount.setPassword(userSignUpDto.getPassword());
//		}
//
//		userAccountRepo.save(userAccount);
//	}

	//--- Form validations ---
	public boolean checkIfAccountExsits(String email) {
		return userAccountRepo.existsByEmail(email);
	}

	public boolean checkIfUsernameExists(String username) {
		return userAccountRepo.existsByUsername(username);
	}

}
