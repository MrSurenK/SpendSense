package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.EditTransactionDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ApiResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionRequestDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.PaginatedResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.TransactionResponse;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import com.MrSurenK.SpendSense_BackEnd.service.SecurityContextService;
import com.MrSurenK.SpendSense_BackEnd.service.TransactionService;
import com.MrSurenK.SpendSense_BackEnd.util.TransactionMapper;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TransactionsController {

    private final TransactionService transactionService;

    private final JwtService jwtService;


    private final SecurityContextService securityContextService;

    public TransactionsController(TransactionService transactionService, JwtService jwtService,
                   SecurityContextService securityContextService){
        this.transactionService = transactionService;
        this.jwtService = jwtService;
        this.securityContextService = securityContextService;
    }

    @PostMapping("/addNewTransaction")
    public ResponseEntity<ApiResponse<TransactionResponse>> addNewTransaction(@RequestBody TransactionRequestDto dto){
        UserAccount user = securityContextService.getUserFromSecurityContext();
        int userId = user.getId();

        Transaction newTransaction = transactionService.addItem(dto, userId);

        ApiResponse<TransactionResponse> res = new ApiResponse<TransactionResponse>();
        TransactionResponse receipt = TransactionMapper.mapEntityToTransactionResponseDto(newTransaction);

        res.setSuccess(true);
        res.setMessage("Transaction added successfully!");
        res.setData(receipt);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<PaginatedResponse<TransactionResponse>> getTranactionsSortedByLastUpdated(
            @PageableDefault(page = 0, size = 5, sort = "lastUpdated", direction = Sort.Direction.DESC) Pageable page){
//        Pageable pageDetails = PageRequest.of(1, 5, Sort.by("lastUpdated")
//                    .descending());

        UserAccount user = securityContextService.getUserFromSecurityContext();
        Integer userId = user.getId();
        Page<Transaction> allTransactions = transactionService.getAllTransactions(userId,page);
        List<Transaction> getTransactions = allTransactions.getContent();

              //Pass data to generif response dto
        PaginatedResponse<TransactionResponse> res = new PaginatedResponse<>();

        List<TransactionResponse> getListOfTransactions = getTransactions.stream()
            .map(TransactionMapper::mapEntityToTransactionResponseDto)
            .collect(Collectors.toList());


        res.setSuccess(true);
        res.setMessage("Successfully retrieved all transactions");
        res.setContent(getListOfTransactions); //Mapped to response object to prevent exposing entity in JSON
        res.setPage(allTransactions.getNumber());
        res.setSize(allTransactions.getSize());
        res.setFirst(allTransactions.isFirst());
        res.setLast(allTransactions.isLast());
        res.setTotalPages(allTransactions.getTotalPages());

        return ResponseEntity.ok(res);
    }


    @PatchMapping("/transactions/{transactionId}")
    public ResponseEntity<ApiResponse<TransactionResponse>> patchTransaction(@PathVariable("transactionId") UUID transactionId,
                                                                @RequestBody EditTransactionDto dto){

        //Get user id from security context
        UserAccount user = securityContextService.getUserFromSecurityContext();
        int userId = user.getId();

        //Perform logic
        Transaction updated = transactionService.editTransaction(dto, userId, transactionId);

        //Get the transaction object and place into response
        TransactionResponse showChanged = TransactionMapper.mapEntityToTransactionResponseDto(updated);

        //Return response DTO with Transaction
        ApiResponse<TransactionResponse> res = new ApiResponse();
        res.setSuccess(true);
        res.setMessage("Transaction successfully modified");
        res.setData(showChanged);


        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable("transactionId") UUID transactionId){
        transactionService.deleteTransaction(transactionId);

        ApiResponse<Void> res = new ApiResponse();
        res.setSuccess(true);
        res.setMessage("Transaction successfully deleted!");

        return ResponseEntity.ok(res);
    }





}
