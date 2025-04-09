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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test User Account Service Layer")
public class UserAccountServiceTest {

    @Mock
    private UserAccountRepo userAccountRepo;

    @InjectMocks
    private UserAccountService userAccountService;


    @Test
    @DisplayName("Test service if email account exists")
    void testUserAccountExistsReturnTrue(){
        String email = "existingUser@gmail.com";
        UserSignUpDto userSignUpDto = new UserSignUpDto();
        userSignUpDto.setEmail(email);

        when(userAccountRepo.existsByEmail(email)).thenReturn(true);

        Exception exception = assertThrows(EmailAlreadyExistsException.class,
                ()-> userAccountService.checkIfAccountExsits(userSignUpDto));

        assertEquals("Account already exists", exception.getMessage());
        //Check that mocked method was called at least once
        verify(userAccountRepo, times(1)).existsByEmail(email);
    }

    @Test
    @DisplayName("Test service if email account deos not exists")
    void testUserAccounExistsReturnFalse(){

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
