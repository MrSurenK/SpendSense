package com.MrSurenK.SpendSense_BackEnd.service_test;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.EditTransactionDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionRequestDto;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.CategoryRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import com.MrSurenK.SpendSense_BackEnd.service.TransactionService;

import com.MrSurenK.SpendSense_BackEnd.util.DateTimeFormats;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("Test funtionality of transactions features")
public class TransactionServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserAccountRepo userAccountRepo;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private CategoryRepo categoryRepo;


    @Nested
    class TransactionCRUD {

         TransactionRequestDto transactionRequestDto;


        @Test
        @DisplayName("Test functionality to add transaction")
        void addTransaction(){
            transactionRequestDto = new TransactionRequestDto();

            BigDecimal amount = new BigDecimal("100.00");
            String remarks = "Test new transaction record added.";
            LocalDate date  = DateTimeFormats.formatStringDate("17-07-2025");
            Category catObject = new Category();
            catObject.setId(3L);
            catObject.setName("Test Category");

            transactionRequestDto.setAmount(amount);
            transactionRequestDto.setRemarks(remarks);
            transactionRequestDto.setRecurring(false);
            transactionRequestDto.setDate(date);
            transactionRequestDto.setCategoryId(3L);

            UserAccount dummyAccount = new UserAccount();
            dummyAccount.setId(1);
            dummyAccount.setUsername("testAccount");

            Category cat = new Category();
            cat.setId(3L);

            when(categoryRepo.getValidCat(1,3L)).thenReturn(Optional.of(cat));
            when(userAccountRepo.findById(1)).thenReturn(Optional.of(dummyAccount));

            transactionService.addItem(transactionRequestDto,dummyAccount.getId());

            ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

            verify(transactionRepo,times(1)).save(captor.capture());

            Transaction transaction = captor.getValue();

            assertEquals(transactionRequestDto.getAmount(),transaction.getAmount());
            assertEquals(transactionRequestDto.getCategoryId(),transaction.getCategory().getId());
            assertEquals(transactionRequestDto.getDate(),transaction.getTransactionDate());
            assertEquals(transactionRequestDto.getRecurring(),transaction.getRecurring());
            assertEquals(transactionRequestDto.getRemarks(),transaction.getRemarks());
            assertEquals(dummyAccount,transaction.getUserAccount());
        }

        @Test
        @DisplayName("Test functionality of editing existing transaction")
        void editTransaction(){
            //Arrange
            Transaction testTransaction = new Transaction();

            Category cat = new Category();
            cat.setId(2L);
            cat.setName("First Cat");

            Category newCat = new Category();
            newCat.setId(3L);
            newCat.setName("New Cat");

            UserAccount user = new UserAccount();
            user.setId(1);

            UUID id = UUID.randomUUID();
            testTransaction.setId(id);
            testTransaction.setTransactionDate(LocalDate.of(2025, 07, 29));
            testTransaction.setLastUpdated(LocalDateTime.of(2025, 07, 20,10,5));

            testTransaction.setCategory(cat);
            testTransaction.setAmount(new BigDecimal("123.45"));
            testTransaction.setRemarks("Test case");
            testTransaction.setRecurring(false);

            EditTransactionDto editForm = new EditTransactionDto();
            editForm.setRemarks("New remark");
            editForm.setRecurring(true);
            editForm.setAmount(new BigDecimal("555.00"));
            editForm.setCategoryId(3L);
            editForm.setDate(LocalDate.of(2025, 04, 03));

            //Act
            when(transactionRepo.findById(id)).thenReturn(Optional.of(testTransaction));
            when(categoryRepo.getValidCat(1, 3L)).thenReturn(Optional.of(newCat));
            transactionService.editTransaction(editForm, user.getId(), id);

            //Assert
            assertEquals(id,testTransaction.getId());
            assertEquals(editForm.getRemarks(),testTransaction.getRemarks());
            assertEquals(editForm.getAmount(),testTransaction.getAmount());
            assertEquals(editForm.getCategoryId(), testTransaction.getCategory().getId());
            assertEquals(editForm.getRecurring(),testTransaction.getRecurring());
            assertEquals(editForm.getDate(), testTransaction.getTransactionDate());
            assertNotEquals(testTransaction.getLastUpdated(),LocalDateTime
                    .of(2025, 07, 20,10,5));


        }

    }

    class NegativeTransactionInputTests{

        void nullType(){

        }

        void nullAmount(){

        }

        void nullCat(){

        }

        void nullDate(){

        }

        void nullRecurring(){

        }

        void nullUserId(){

        }

        void negativeAmount(){

        }

        void invalidDateFormat(){

        }

    }

}
