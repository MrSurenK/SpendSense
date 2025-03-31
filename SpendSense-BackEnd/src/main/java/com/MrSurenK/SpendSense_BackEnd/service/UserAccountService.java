package com.MrSurenK.SpendSense_BackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;

@Service
public class UserAccountService {

	private UserAccountRepo userAccountRepo;
	private UserAccount userAccount = new UserAccount();

	@Autowired
	private UserAccountService(UserAccountRepo userAccountRepo) {
		this.userAccountRepo = userAccountRepo;
	}

	public void createAccount(UserSignUpDto userSignUpDto) {
		if (checkIfAccountExsits(userSignUpDto)) {
			// Map fields to user account
			userAccount.setEmail(userSignUpDto.getEmail());
		}
	}

	public boolean checkIfAccountExsits(UserSignUpDto userSignUpDto) {
		Iterable<UserAccount> allAccounts = userAccountRepo.findAll();
		for (UserAccount eachAccount : allAccounts) {
			if (userAccount.getEmail().equals(userSignUpDto.getEmail())) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean checkIfUsernameExists() {
		return true;
		// ToDo: Check if username exits in db and handle exceptions so that client will
		// be able to know on front end
	}

}
