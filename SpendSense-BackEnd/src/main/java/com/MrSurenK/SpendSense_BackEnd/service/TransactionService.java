package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.dto.TransactionDto;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {


    private final TransactionRepo transactionRepo;

    private final UserAccountRepo userAccountRepo;

    private final JwtService jwtService;

    public TransactionService(TransactionRepo transactionRepo, UserAccountRepo userAccountRepo, JwtService jwtService){
        this.transactionRepo = transactionRepo;
        this.userAccountRepo = userAccountRepo;
        this.jwtService = jwtService;
    }

    //CRUD Functionality

    public void addItem(TransactionDto transactionDto, String token
                        ){
        Transaction newItem = new Transaction();

        newItem.setAmount(transactionDto.getAmount());
        newItem.setCategory(transactionDto.getCategory());
        newItem.setTransactionDate(transactionDto.getDate());
        newItem.setRecurring(transactionDto.getRecurring() != null ? transactionDto.getRecurring(): false);

        //Get userAccount from jwt token
        String username = jwtService.extractUsername(token);
        UserAccount userAcc = userAccountRepo.findByUsername(username).orElseThrow();
        newItem.setUserAccount(userAcc);

        newItem.setRemarks(transactionDto.getRemarks());

        transactionRepo.save(newItem);
    }

    //Pass JWT token to extract username and get User Id and also Pageable object with page details in controller
    public Page<Transaction> getAllTransactions(UserAccount user, Pageable page){
        //Get the user id from user account
        int userId = user.getId();
        return transactionRepo.findByUserAccountId(userId,page);
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
