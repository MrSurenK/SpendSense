package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ApiResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionRequestDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.PaginatedResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.TransactionResponse;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import com.MrSurenK.SpendSense_BackEnd.service.SecurityContextService;
import com.MrSurenK.SpendSense_BackEnd.service.TransactionService;
import com.MrSurenK.SpendSense_BackEnd.util.TransactionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ApiResponse<TransactionResponse>> addNewTransaction(@RequestBody TransactionRequestDto dto){
        UserAccount user = securityContextService.getUserFromSecurityContext();
        int userId = user.getId();

        Transaction newTransaction = transactionService.addItem(dto, userId);

        ApiResponse<TransactionResponse> res = new ApiResponse<TransactionResponse>();
        TransactionResponse receipt = TransactionMapper.mapEntityToTransactionResponseDto(newTransaction);
//        TransactionResponse reciept = new TransactionResponse();
//        reciept.setId(newTransaction.getId());
//        reciept.setAmount(newTransaction.getAmount());
//        reciept.setTransactionDate(newTransaction.getTransactionDate());
//        reciept.setCatName(newTransaction.getCategory().getName());
//        reciept.setRecurring(newTransaction.getRecurring());
//        reciept.setRemarks(newTransaction.getRemarks());
//        reciept.setLastUpdated(newTransaction.getLastUpdated());

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

        List<TransactionResponse> getListOfTransactions = getTransactions.stream()
            .map(TransactionMapper::mapEntityToTransactionResponseDto)
            .collect(Collectors.toList());


        //Pass data to generif response dto
        PaginatedResponse<TransactionResponse> res = new PaginatedResponse<>();
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



}
