package com.MrSurenK.SpendSense_BackEnd.service_test;

import com.MrSurenK.SpendSense_BackEnd.Exception.EmailAlreadyExistsException;
import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.UserAccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test User Account Service Layer")
public class UserAccountServiceTest {

    @Mock
    private UserAccountRepo userAccountRepo;

    @InjectMocks
    private UserAccountService userAccountService;


    @Test
    @DisplayName("Test user sign-up if email account exists")
    void testUserAccountExistsReturnTrue(){
        String email = "existingUser@gmail.com";
        when(userAccountRepo.existsByEmail(email)).thenReturn(true);
        //Asserts
        assertTrue(userAccountService.checkIfAccountExsits(email));
        //Check that mocked method was called at least once
        verify(userAccountRepo, times(1)).existsByEmail(email);
    }

    @Test
    @DisplayName("Test user sign-up if email account deos not exists")
    void testUserAccounExistsReturnFalse(){
        String email = "newSignUp@gmal.com";
        //Mock repo
        when(userAccountRepo.existsByEmail(email)).thenReturn(false);
        //Asserts
        assertFalse(userAccountService.checkIfAccountExsits(email));
        verify(userAccountRepo, times(1)).existsByEmail(email);
    }

    @Test
    @DisplayName("Test if username is taken")
    void testUsernameTaken(){
        String username = "newUser";
        when(userAccountRepo.existsByUsername(username)).thenReturn(true);

        assertTrue(userAccountService.checkIfUsernameExists(username));
        verify(userAccountRepo, times(1)).existsByUsername(username);

    }

    @Test
    @DisplayName("Test if username is available")
    void testUsernameAvailable(){
        String username = "newUser";
        when(userAccountRepo.existsByUsername(username)).thenReturn(false);

        assertFalse(userAccountService.checkIfUsernameExists(username));
        verify(userAccountRepo, times(1)).existsByUsername(username);
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
        //ToDo: Format date time properly such that only date without time is stored in DB
    }
}
