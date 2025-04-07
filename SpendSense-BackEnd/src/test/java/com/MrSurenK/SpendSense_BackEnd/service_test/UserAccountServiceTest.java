package com.MrSurenK.SpendSense_BackEnd.service_test;

import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.UserAccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("Test User Account Service Layer")
public class UserAccountServiceTest {

    @Mock
    private UserAccountRepo userAccountRepo;

    @InjectMocks
    private UserAccountService userAccountService;


    @Test
    void testUserAccountExists(){

    }

    @Test
    void testUsernameTaken(){

    }

    @Test
    void testUserAccountCreated(){

    }

    @Test
    void testUserAccountCreationFail(){

    }

    @Test
    void testUserInvalidEmail(){

    }

    @Test
    void testInvalidDateFormat(){

    }



}
