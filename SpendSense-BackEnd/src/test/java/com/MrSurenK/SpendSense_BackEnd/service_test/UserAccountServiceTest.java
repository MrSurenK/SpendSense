package com.MrSurenK.SpendSense_BackEnd.service_test;

import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
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


    @Nested
    @DisplayName("When a user fills up the sign up form")
    class TestAccountCreation {

        UserSignUpDto userSignUpDto;
        Map<String, String> json;
        UserAccount userAccount;

        @BeforeEach
        void setUp(){
            String email = "test@gmail.com";
            String username = "newUser";
            String firstName = "firstName";
            String lastName = "lastName";
            String dob = "01-01-2025";
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dateTime = null;
            try {
                dateTime = LocalDate.parse(dob, pattern);
                System.out.println(dateTime);
            } catch (DateTimeParseException e) {
                // DateTimeParseException - Text '2019-nov-12' could not be parsed at index 5
                log.error("e: ", e);
                // Exception handling message/mechanism/logging as per company standard
            }
            String password = "pass";

            userSignUpDto = new UserSignUpDto();
            userSignUpDto.setEmail(email);
            userSignUpDto.setUserName(username);
            userSignUpDto.setFirstName(firstName);
            userSignUpDto.setLastName(lastName);
            userSignUpDto.setDob(dateTime);
            userSignUpDto.setPassword(password);
        }

        @Test
        @DisplayName("if he filled all the fields accordingly, he should have sucesssfully created an account")
        void testUserAccountCreated(){


            when(userAccountRepo.save(userAccount)).thenReturn(userAccount);
            when(userAccountRepo.existsByEmail(userSignUpDto.getEmail())).thenReturn(false);
            when(userAccountRepo.existsByUsername(userSignUpDto.getUserName())).thenReturn(false);

            userAccountService.createAccount(userSignUpDto);

            assertEquals(userAccount.getEmail(),userSignUpDto.getEmail());
            assertEquals(userAccount.getUsername(), userSignUpDto.getUserName());
            assertEquals(userAccount.getFirstName(),userSignUpDto.getFirstName());
            assertEquals(userAccount.getLastName(),userSignUpDto.getLastName());
            assertEquals(userAccount.getDateOfBirth(),userSignUpDto.getDob());
            assertEquals(userAccount.getPassword(),userSignUpDto.getPassword());

        }

        @Test
        @DisplayName("If he did not fill any field then an exception should be thrown")
        void testUserAccountCreationFail(){

        }

        @Test
        @DisplayName("If he did not fill in an acceptable email format then an exception should be thrown")
        void testUserInvalidEmail(){

        }

        @Test
        @DisplayName("If the date format he gave is incorrect an exception should be thrown")
        void testInvalidDateFormat(){
            String newDate = "2025-01-01";


        }
    }

}
