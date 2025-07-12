package com.MrSurenK.SpendSense_BackEnd.service_test;

import com.MrSurenK.SpendSense_BackEnd.dto.UserSignUpDto;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j //Add loggers
@ExtendWith(MockitoExtension.class)
@DisplayName("Test User Account Service Layer")
public class UserAccountServiceTest {

    @Mock
    private UserAccountRepo userAccountRepo;

    @InjectMocks
    private UserAccountService userAccountService;

    @Mock
    private PasswordEncoder passwordEncoder;


    UserSignUpDto userSignUpDto;


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

        when(userAccountRepo.existsByEmail(userSignUpDto.getEmail())).thenReturn(false);
        when(userAccountRepo.existsByUsername(userSignUpDto.getUserName())).thenReturn(false);
        //mock password encoder
        when(passwordEncoder.encode(userSignUpDto.getPassword())).thenReturn("encoded-password");
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);


        assertDoesNotThrow(()->userAccountService.createAccount(userSignUpDto));
        verify(userAccountRepo, times(1)).save(captor.capture());

        UserAccount userAccount = captor.getValue();



        assertEquals(userSignUpDto.getEmail(),userAccount.getEmail());
        assertEquals(userSignUpDto.getUserName(),userAccount.getUsername());
        assertEquals(userSignUpDto.getFirstName(),userAccount.getFirstName());
        assertEquals(userSignUpDto.getLastName(),userAccount.getLastName());
        assertEquals(userSignUpDto.getDob(),userAccount.getDateOfBirth());
        assertEquals("encoded-password", userAccount.getPassword());

        }

    @Test
    @DisplayName("Test to check if account creation throws exception if email exists in db")
    void testIfEmailExists(){

        when(userAccountRepo.existsByEmail(userSignUpDto.getEmail())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, ()-> {
            userAccountService.createAccount(userSignUpDto);
        });

        String expectedMessage = "Email already exists: " + userSignUpDto.getEmail();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Test to check if account creation throws exception if username exists in db")
    void testIfUsernameExists(){

        when(userAccountRepo.existsByEmail(userSignUpDto.getEmail())).thenReturn(false);
        when(userAccountRepo.existsByUsername(userSignUpDto.getUserName())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, ()-> {
            userAccountService.createAccount(userSignUpDto);
        });

        String expectedMessage = "Username already exists: " + userSignUpDto.getUserName();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
