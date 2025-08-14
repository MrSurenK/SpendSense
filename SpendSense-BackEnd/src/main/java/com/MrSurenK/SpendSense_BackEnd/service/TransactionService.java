package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.EditTransactionDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionRequestDto;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.repository.CategoryRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.LocalDateTime;
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
        newItem.setTransactionDate(transactionRequestDto.getDate());
        newItem.setRecurring(transactionRequestDto.getRecurring() != null ? transactionRequestDto.getRecurring(): false);

        //Get userAccount from Security Context

        newItem.setUserAccount(userAccountRepo.findById(userId).orElseThrow());

        newItem.setRemarks(transactionRequestDto.getRemarks());

        newItem.setLastUpdated(LocalDateTime.now());

        transactionRepo.save(newItem);

        return newItem;
    }

    //Getting and sorting transactions by specific filters

    //Pass JWT token to extract username and get User Id and also Pageable object with page details in controller
    public Page<Transaction> getTransactions(Integer userId, Pageable page){
        //Get the user id from user account
        return transactionRepo.findAllByUserAccountId(userId,page);
    }


    //ToDo: More filter options to retrieve transactions by
//    public Page<Transaction>getByCategoryFilter(Integer userId, Long catId, Pageable page){
//        return transactionRepo.findAllByCategoryIdAndUserAccountId(catId,userId,page);
//    }
//
//    public Page<Transaction>getBetweenDatesFilter(LocalDate startDate, LocalDate endDate, Integer userId,
//                                                  Pageable page){
//        return transactionRepo.findAllByUserAccountIdAndTransactionDateBetween(startDate, endDate, userId, page);
//    }



    public Transaction editTransaction(EditTransactionDto dto, int userId, UUID transactionId){
        //Get transaction that is being edited
        Transaction currTransaction = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        //Update new information in form
        if(dto.getAmount() != null){
            currTransaction.setAmount(dto.getAmount());
        }
        if(dto.getDate() != null){
            currTransaction.setTransactionDate(dto.getDate());
        }
        if(dto.getRemarks() != null){
            currTransaction.setRemarks(dto.getRemarks());
        }
        if(dto.getRecurring() != null){
            currTransaction.setRecurring(dto.getRecurring());
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

    //Filter functionalities
    public void filterByDate(){

    }

    public void filterByCat(){

    }



}
