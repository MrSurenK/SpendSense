package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionDto;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.CategoryRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionService {


    private final TransactionRepo transactionRepo;

    private final UserAccountRepo userAccountRepo;

    private final JwtService jwtService;

    private final CategoryRepo categoryRepo;

    public TransactionService(TransactionRepo transactionRepo, UserAccountRepo userAccountRepo, JwtService jwtService
    ,CategoryRepo categoryRepo){
        this.transactionRepo = transactionRepo;
        this.userAccountRepo = userAccountRepo;
        this.jwtService = jwtService;
        this.categoryRepo = categoryRepo;
    }

    //CRUD Functionality

    public Transaction addItem(TransactionDto transactionDto, UserAccount userAccount
                        ){
        Transaction newItem = new Transaction();

        newItem.setAmount(transactionDto.getAmount().setScale(2, RoundingMode.HALF_UP));

        Category cat = categoryRepo.getReferenceById(transactionDto.getCategoryId());
        newItem.setCategory(cat);
        newItem.setTransactionDate(transactionDto.getDate());
        newItem.setRecurring(transactionDto.getRecurring() != null ? transactionDto.getRecurring(): false);

        //Get userAccount from jwt token

        newItem.setUserAccount(userAccount);

        newItem.setRemarks(transactionDto.getRemarks());

        newItem.setLastUpdated(LocalDateTime.now());

        transactionRepo.save(newItem);

        return newItem;
    }

    //Getting and sorting transactions by specific filters

    //Pass JWT token to extract username and get User Id and also Pageable object with page details in controller
    public Page<Transaction> getAllTransactions(UserAccount userAccount, Pageable page){
        //Get the user id from user account
        return transactionRepo.findAllByUserAccountId(userAccount,page);
    }

    public Page<Transaction>getByCategoryFilter(UserAccount userAccount, Long catId, Pageable page){
        return transactionRepo.findAllByCategoryIdAndUserAccountId(catId,userAccount,page);
    }

    public Page<Transaction>getBetweenDatesFilter(LocalDate startDate, LocalDate endDate, UserAccount userAccount,
                                                  Pageable page){
        return transactionRepo.findAllByUserAccountIdAndTransactionDateBetween(startDate, endDate, userAccount, page);
    }









    public void editTransaction(){
        //Get transaction that is being edited
        //Update new information in form
        //Save transaction


    }

    public void deleteTransaction(){
        //Get transaction id
        //Perform delete operation of transaction record from db
    }

    //Filter functionalities
    public void filterByDate(){

    }

    public void filterByCat(){

    }



}
