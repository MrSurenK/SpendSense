package com.MrSurenK.SpendSense_BackEnd.service_test;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionDto;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    class TransactonCRUD {

         TransactionDto transactionDto;


        @Test
        @DisplayName("Test functionality to add transaction")
        void addTransaction(){
            transactionDto = new TransactionDto();

            BigDecimal amount = new BigDecimal("100.00");
            String remarks = "Test new transaction record added.";
            LocalDate date  = DateTimeFormats.formatStringDate("17-07-2025");
            Category catObject = new Category();
            catObject.setId(3L);
            catObject.setName("Test Category");

            transactionDto.setAmount(amount);
            transactionDto.setRemarks(remarks);
            transactionDto.setRecurring(false);
            transactionDto.setDate(date);
            transactionDto.setCategoryId(3L);

            UserAccount dummyAccount = new UserAccount();
            dummyAccount.setId(1);
            dummyAccount.setUsername("testAccount");

            Category cat = new Category();
            cat.setId(3L);

//            String token = "token";
//            when(jwtService.extractUsername(token)).thenReturn("testUser");
//            when(userAccountRepo.findByUsername("testUser")).thenReturn(Optional.of(dummyAccount));
            when(categoryRepo.getReferenceById(3L)).thenReturn(cat);

            transactionService.addItem(transactionDto,dummyAccount);
            ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

            verify(transactionRepo,times(1)).save(captor.capture());

            Transaction transaction = captor.getValue();

            assertEquals(transactionDto.getAmount(),transaction.getAmount());
            assertEquals(transactionDto.getCategoryId(),transaction.getCategory().getId());
            assertEquals(transactionDto.getDate(),transaction.getTransactionDate());
            assertEquals(transactionDto.getRecurring(),transaction.getRecurring());
            assertEquals(transactionDto.getRemarks(),transaction.getRemarks());
            assertEquals(dummyAccount,transaction.getUserAccount());

        }



        void getAllTransactions(){

        }
        void editTransaction(){

        }

        void deleteTransaction(){

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

    class CategoryCRUDTests{

        void addCat(){

        }

        void updateCat(){

        }

        void deleteCat(){

        }

    }

}
