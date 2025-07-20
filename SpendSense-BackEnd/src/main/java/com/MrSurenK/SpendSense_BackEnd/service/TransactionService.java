package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.dto.TransactionDto;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import io.jsonwebtoken.Claims;

public class TransactionService {


    private final TransactionRepo transactionRepo;

    private final UserAccountRepo userAccountRepo;

    private final JwtService jwtService;

    public TransactionService(TransactionRepo transactionRepo, UserAccountRepo userAccountRepo, JwtService jwtService){
        this.transactionRepo = transactionRepo;
        this.userAccountRepo = userAccountRepo;
        this.jwtService = jwtService;
    }

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

}
