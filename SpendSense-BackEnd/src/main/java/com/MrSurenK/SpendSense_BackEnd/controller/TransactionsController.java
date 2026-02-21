package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.EditTransactionDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionFiltersDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ApiResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionRequestDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.PaginatedResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.TransactionResponse;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.TransactionType;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import com.MrSurenK.SpendSense_BackEnd.service.SecurityContextService;
import com.MrSurenK.SpendSense_BackEnd.service.TransactionService;
import com.MrSurenK.SpendSense_BackEnd.util.EntityToDtoMapper;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<TransactionResponse>> addNewTransaction(@RequestBody TransactionRequestDto dto){
        UserAccount user = securityContextService.getUserFromSecurityContext();
        int userId = user.getId();

        Transaction newTransaction = transactionService.addItem(dto, userId);

        ApiResponse<TransactionResponse> res = new ApiResponse<TransactionResponse>();
        TransactionResponse receipt = EntityToDtoMapper.mapEntityToTransactionResponseDto(newTransaction);

        res.setSuccess(true);
        res.setMessage("Transaction added successfully!");
        res.setData(receipt);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/transactions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginatedResponse<TransactionResponse>> getAllTranactionsSortedByLastUpdated(
            TransactionFiltersDto transactionFiltersDto,
            @PageableDefault(page = 0, size = 10, sort = "lastUpdated", direction = Sort.Direction.DESC) Pageable page){

        UserAccount user = securityContextService.getUserFromSecurityContext();
        Integer userId = user.getId();

        //Get all the required fields from transactionFilters DTO
//        LocalDate startDate = transactionFiltersDto.getStartDate();
//        LocalDate endDate = transactionFiltersDto.getEndDate();
//        BigDecimal min = transactionFiltersDto.getMin();
//        BigDecimal max = transactionFiltersDto.getMax();
//        Integer catId = transactionFiltersDto.getCatId();
//        TransactionType transactionType = transactionFiltersDto.getTransactionType();
//        Boolean isRecurring = transactionFiltersDto.getIsRecurring();
//        String title = transactionFiltersDto.getTitle();


        Page<Transaction> allTransactions = transactionService.getTransactions(userId,
                transactionFiltersDto,
                page);
        List<Transaction> getTransactions = allTransactions.getContent();

              //Pass data to generif response dto
        PaginatedResponse<TransactionResponse> res = new PaginatedResponse<>();

        List<TransactionResponse> getListOfTransactions = getTransactions.stream()
            .map(EntityToDtoMapper::mapEntityToTransactionResponseDto)
            .collect(Collectors.toList());


        res.setSuccess(true);
        res.setMessage("Successfully retrieved all transactions");
        res.setContent(getListOfTransactions); //Mapped to response object to prevent exposing entity in JSON
        res.setPage(allTransactions.getNumber());
        res.setTotal(allTransactions.getTotalElements());
        res.setSize(allTransactions.getSize());
        res.setFirst(allTransactions.isFirst());
        res.setLast(allTransactions.isLast());
        res.setTotalPages(allTransactions.getTotalPages());

        return ResponseEntity.ok(res);
    }

//    @GetMapping("/txn/getTxnWithinDateRange")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<PaginatedResponse<TransactionResponse>> getTransactionsInRange(
//            @RequestParam LocalDate startDate,
//            @RequestParam LocalDate endDate,
//            @PageableDefault(page = 0, size = 10, sort = "lastUpdated", direction = Sort.Direction.DESC) Pageable page
//    ){
//        int userid = securityContextService.getUserFromSecurityContext().getId();
//
//        Page<Transaction> getFilteredTxn = transactionService.getTransactionWithDateRange(userid,startDate,endDate,page);
//
//        List<Transaction> txns = getFilteredTxn.getContent();
//
//        PaginatedResponse<TransactionResponse> res = new PaginatedResponse<>();
//
//        List<TransactionResponse> getListOfTransactions = txns.stream()
//                .map(EntityToDtoMapper::mapEntityToTransactionResponseDto)
//                .toList();
//
//        res.setSuccess(true);
//        res.setMessage("Successfully retrieved transactions from " + startDate + " to " + endDate);
//        res.setContent(getListOfTransactions); //Mapped to response object to prevent exposing entity in JSON
//        res.setPage(getFilteredTxn.getNumber());
//        res.setSize(getFilteredTxn.getSize());
//        res.setFirst(getFilteredTxn.isFirst());
//        res.setLast(getFilteredTxn.isLast());
//        res.setTotalPages(getFilteredTxn.getTotalPages());
//
//        return ResponseEntity.ok(res);
//    }


    @PatchMapping("/transactions/{transactionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<TransactionResponse>> patchTransaction(@PathVariable("transactionId") UUID transactionId,
                                                                @RequestBody EditTransactionDto dto){

        //Get user id from security context
        UserAccount user = securityContextService.getUserFromSecurityContext();
        int userId = user.getId();

        //Perform logic
        Transaction updated = transactionService.editTransaction(dto, userId, transactionId);

        //Get the transaction object and place into response
        TransactionResponse showChanged = EntityToDtoMapper.mapEntityToTransactionResponseDto(updated);

        //Return response DTO with Transaction
        ApiResponse<TransactionResponse> res = new ApiResponse();
        res.setSuccess(true);
        res.setMessage("Transaction successfully modified");
        res.setData(showChanged);


        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/transactions/{transactionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable("transactionId") UUID transactionId){
        transactionService.deleteTransaction(transactionId);

        ApiResponse<Void> res = new ApiResponse();
        res.setSuccess(true);
        res.setMessage("Transaction successfully deleted!");

        return ResponseEntity.ok(res);
    }
}
