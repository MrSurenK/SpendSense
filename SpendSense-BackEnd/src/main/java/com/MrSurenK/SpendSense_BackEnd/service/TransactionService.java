package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.JPASpecifications.TransactionSpecifications;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.EditTransactionDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionFiltersDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionRequestDto;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.TransactionType;
import com.MrSurenK.SpendSense_BackEnd.repository.CategoryRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {


    private final TransactionRepo transactionRepo;

    private final UserAccountRepo userAccountRepo;

    private final CategoryRepo categoryRepo;

    public TransactionService(TransactionRepo transactionRepo, UserAccountRepo userAccountRepo, JwtService jwtService
    ,CategoryRepo categoryRepo){
        this.transactionRepo = transactionRepo;
        this.userAccountRepo = userAccountRepo;
        this.categoryRepo = categoryRepo;
    }

    //CRUD Functionality

    public Transaction addItem(TransactionRequestDto transactionRequestDto, Integer userId
                        ){
        Transaction newItem = new Transaction();

        newItem.setAmount(transactionRequestDto.getAmount().setScale(2, RoundingMode.HALF_UP));

        Category cat = categoryRepo.getValidCat(userId, transactionRequestDto.getCategoryId()).orElseThrow(()
                ->new EntityNotFoundException("Category not found"));
        newItem.setCategory(cat);
        LocalDate txnDate = transactionRequestDto.getDate();
        newItem.setTransactionDate(txnDate);
        newItem.setRecurring(transactionRequestDto.getRecurring() != null ? transactionRequestDto.getRecurring(): false);

        if(transactionRequestDto.getRecurring()==true){
            //Add 1 mth to due date field
            newItem.setNextDueDate(txnDate.plusMonths(1));
        }

        //Get userAccount from Security
        newItem.setUserAccount(userAccountRepo.findById(userId).orElseThrow());

        newItem.setTitle(transactionRequestDto.getTitle());

        newItem.setRemarks(transactionRequestDto.getRemarks());

        newItem.setLastUpdated(LocalDateTime.now());

        transactionRepo.save(newItem);

        return newItem;
    }

    //Getting and sorting transactions by specific filters

    //Pass JWT token to extract username and get User Id and also Pageable object with page details in controller
    public Page<Transaction> getTransactions(Integer userId,
   TransactionFiltersDto transactionFiltersDto,
                                             Pageable page){
        //Get the user id from user account
       //return transactionRepo.findAllByUserAccountId(userId,page);
        return transactionRepo.findAll(
                Specification.where(TransactionSpecifications.hasUser(userId))
                        .and(TransactionSpecifications.withinDateRange(transactionFiltersDto.getStartDate(),
                                transactionFiltersDto.getEndDate()))
                        .and(TransactionSpecifications.amountBetween(transactionFiltersDto.getMin(),
                                transactionFiltersDto.getMax()))
                        .and(TransactionSpecifications.hasCategory(transactionFiltersDto.getCatId()))
                        .and(TransactionSpecifications.hasExpenseOrIncome(transactionFiltersDto.getTransactionType()))
                        .and(TransactionSpecifications.isRecurringFilter(transactionFiltersDto.getIsRecurring()))
                        .and(TransactionSpecifications.hasTitle(transactionFiltersDto.getTitle())),
                         page
        );
    }


    public Transaction editTransaction(EditTransactionDto dto, int userId, UUID transactionId){
        //Get transaction that is being edited
        Transaction currTransaction = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        //Update new information in form
        if(dto.getAmount() != null){
            currTransaction.setAmount(dto.getAmount());
        }
        if(dto.getDate() != null){
            LocalDate newDate = dto.getDate();
            currTransaction.setTransactionDate(newDate);
            //Check if recurring is true in entity and if so update the next Due Date as well
            if(currTransaction.getRecurring() == true){
                currTransaction.setNextDueDate(newDate.plusMonths(1));
            }
        }
        if(dto.getTitle() != null){
            currTransaction.setTitle(dto.getTitle());
        }
        if(dto.getRemarks() != null){
            currTransaction.setRemarks(dto.getRemarks());
        }
        if(dto.getRecurring() != null){
            currTransaction.setRecurring(dto.getRecurring());
            //remove next due date if recurring changed to false
            if(dto.getRecurring() == false){
                if(!(currTransaction.getNextDueDate() == null)){
                    currTransaction.setNextDueDate(null);
                }
            }
        }
        if(dto.getCategoryId() != null){
            Category newCat = categoryRepo.getValidCat(userId, dto.getCategoryId()).orElseThrow(()
                    -> new EntityNotFoundException("Category not found"));
            currTransaction.setCategory(newCat);
        }

        //Update lastUpdated field
        currTransaction.setLastUpdated(LocalDateTime.now());

        //Save and update transaction uin database
        transactionRepo.save(currTransaction);

        return currTransaction;

    }

    public void deleteTransaction(UUID transactionId){
        //Get transaction id
        transactionRepo.deleteById(transactionId);
        //Perform delete operation of transaction record from db
    }

    @Transactional
    public void generateNewRecurringTransactions(){
        LocalDate today = LocalDate.now();

        List<Transaction> templatesDueToday = transactionRepo.findByRecurringTrueAndNextDueDate(today);

        for(Transaction template: templatesDueToday){

            Transaction copy = new Transaction();
            copy.setParentTransaction(template);
            copy.setUserAccount(template.getUserAccount());
            copy.setAmount(template.getAmount());
            copy.setCategory(template.getCategory());
            copy.setTransactionDate(today);
            copy.setRecurring(false); //copies shd never be recurring to prevent infiinite cascade of copies
            copy.setLastUpdated(LocalDateTime.now());
            copy.setRemarks(template.getRemarks());

            transactionRepo.save(copy);

            template.setNextDueDate(today.plusMonths(1));
            transactionRepo.save(template);
        }
    }
}
