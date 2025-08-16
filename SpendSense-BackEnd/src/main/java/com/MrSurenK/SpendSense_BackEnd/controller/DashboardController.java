package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ApiResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.DashTopFiveSpendDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.PaginatedResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.TransactionResponse;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.service.DashboardService;
import com.MrSurenK.SpendSense_BackEnd.service.JwtService;
import com.MrSurenK.SpendSense_BackEnd.service.SecurityContextService;
import com.MrSurenK.SpendSense_BackEnd.service.TransactionService;
import com.MrSurenK.SpendSense_BackEnd.util.EntityToDtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ApiResponse<List<DashTopFiveSpendDto>>> getTopFiveTransactionsCatAndAmount(
            @RequestParam(required = false) Integer month,
            @RequestParam Integer year
    ){

        Integer userId = securityContextService.getUserFromSecurityContext().getId();

        List<Transaction> topFiveTransactions = dashboardService.getTop5TransactionsByAmountForMonthOrYear(
                userId, month, year);
        List<DashTopFiveSpendDto> topFiveSpends= new ArrayList<>();

        for(Transaction each: topFiveTransactions){
           DashTopFiveSpendDto newEntry =  EntityToDtoMapper.mapEntityToDashTopFiveDto(each);
           topFiveSpends.add(newEntry);
        }

        ApiResponse<List<DashTopFiveSpendDto>> apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Top 5 transactions retrieved");
        apiResponse.setData(topFiveSpends);


        return ResponseEntity.ok(apiResponse);
    }

}
