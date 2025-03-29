package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.controller.UserAccountController;
import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserAccountService {

    private UserAccountRepo userAccountRepo;
    private UserAccount userAccount;
    private UserSignUpDto userSignUpDto;

    @Autowired
    private UserAccountService(UserAccountRepo userAccountRepo,
                               UserAccount userAccount,
                               UserSignUpDto userSignUpDto){
        this.userAccountRepo = userAccountRepo;
        this.userAccount = userAccount;
        this.userSignUpDto = userSignUpDto;
    }

    public void createAccount(){
        if(checkIfAccountExsits()){
           //Map fields to user account
           userAccount.setEmail(userSignUpDto.getEmail());
        }
    }


    public boolean checkIfAccountExsits() {
        Iterable<UserAccount> allAccounts = userAccountRepo.findAll();
        for (UserAccount eachAccount : allAccounts) {
            if (userAccount.getEmail() == userSignUpDto.getEmail()) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    public boolean checkIfUsernameExists(){
        return true;
        //ToDo: Check if username exits in db and handle exceptions so that client will be able to know on front end
    }



}
