package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

    UserAccountRepo userAccountRepo;

    public SecurityContextService(UserAccountRepo userAccountRepo){
        this.userAccountRepo = userAccountRepo;
    }

    public UserAccount getUserFromSecurityContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userAccountRepo.findByUsername(username).orElseThrow();
    }
}
