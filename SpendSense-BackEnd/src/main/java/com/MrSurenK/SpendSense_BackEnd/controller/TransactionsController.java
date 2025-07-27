package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ApiResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.NewTransactionResponse;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import com.MrSurenK.SpendSense_BackEnd.service.SecurityContextService;
import com.MrSurenK.SpendSense_BackEnd.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    private final TransactionService transactionService;

    private final JwtService jwtService;

    private final UserAccountRepo userAccountRepo;

    private final SecurityContextService securityContextService;

    public TransactionsController(TransactionService transactionService, JwtService jwtService,
                                  UserAccountRepo userAccountRepo, SecurityContextService securityContextService){
        this.transactionService = transactionService;
        this.jwtService = jwtService;
        this.userAccountRepo = userAccountRepo;
        this.securityContextService = securityContextService;
    }

    @PostMapping("/addNewTransaction")
    public ResponseEntity<ApiResponse<NewTransactionResponse>> addNewTransaction(@RequestBody TransactionDto dto){
        UserAccount user = securityContextService.getUserFromSecurityContext();

        Transaction newTransaction = transactionService.addItem(dto, user);

        ApiResponse<NewTransactionResponse> res = new ApiResponse<NewTransactionResponse>();
        NewTransactionResponse reciept = new NewTransactionResponse();
        reciept.setId(newTransaction.getId());
        reciept.setAmount(newTransaction.getAmount());
        reciept.setTransactionDate(newTransaction.getTransactionDate());
        reciept.setCatName(newTransaction.getCategory().getName());
        reciept.setRecurring(newTransaction.getRecurring());
        reciept.setRemarks(newTransaction.getRemarks());
        reciept.setLastUpdated(newTransaction.getLastUpdated());

        res.setSuccess(true);
        res.setMessage("Transaction added successfully!");
        res.setData(reciept);

        return ResponseEntity.ok(res);
    }



}
