package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ApiResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.DashSpendOverview;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.service.DashboardService;
import com.MrSurenK.SpendSense_BackEnd.service.SecurityContextService;
import com.MrSurenK.SpendSense_BackEnd.service.TransactionService;
import com.MrSurenK.SpendSense_BackEnd.util.EntityToDtoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DashboardController {


    private final TransactionService transactionService;

    private final SecurityContextService securityContextService;

    private final DashboardService dashboardService;

    public DashboardController(TransactionService transactionService,SecurityContextService securityContextService,
                               DashboardService dashboardService){
        this.transactionService = transactionService;
        this.securityContextService = securityContextService;
        this.dashboardService = dashboardService;
    }
    
    //Displays top 5 monthly spend items
    @GetMapping("/dash/topFiveSpend")
    public ResponseEntity<ApiResponse<List<DashSpendOverview>>> getTopFiveTransactionsCatAndAmount(
            @RequestParam(required = false) Integer month,
            @RequestParam Integer year
    ){

        Integer userId = securityContextService.getUserFromSecurityContext().getId();

        List<Transaction> topFiveTransactions = dashboardService.getTop5TransactionsByAmountForMonthOrYear(
                userId, month, year);
        List<DashSpendOverview> topFiveSpends= new ArrayList<>();

        for(Transaction each: topFiveTransactions){
           DashSpendOverview newEntry =  EntityToDtoMapper.mapEntityToDashTopFiveDto(each);
           topFiveSpends.add(newEntry);
        }

        ApiResponse<List<DashSpendOverview>> apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Top 5 transactions retrieved");
        apiResponse.setData(topFiveSpends);


        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/dash/subscriptions")
    public ResponseEntity<ApiResponse<List<DashSpendOverview>>> allRecurringSpend(
            @PageableDefault(page=0,size=5,sort="amount",direction = Sort.Direction.DESC) Pageable pageable
    ){
        Integer userId = securityContextService.getUserFromSecurityContext().getId();
        List<Transaction> allRecurringSpendItems = dashboardService.getAllRecurringSpend(userId,pageable);
        List<DashSpendOverview> recurringSpend= new ArrayList<>();

        for(Transaction each: allRecurringSpendItems){
           DashSpendOverview newEntry =  EntityToDtoMapper.mapEntityToDashTopFiveDto(each);
           recurringSpend.add(newEntry);
        }

        ApiResponse<List<DashSpendOverview>> apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Top 5 transactions retrieved");
        apiResponse.setData(recurringSpend);

        return ResponseEntity.ok(apiResponse);
    }

}
