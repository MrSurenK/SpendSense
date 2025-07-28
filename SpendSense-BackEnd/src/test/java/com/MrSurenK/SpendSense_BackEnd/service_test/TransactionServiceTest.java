package com.MrSurenK.SpendSense_BackEnd.service_test;

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

            when(categoryRepo.getReferenceById(3L)).thenReturn(cat);
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
