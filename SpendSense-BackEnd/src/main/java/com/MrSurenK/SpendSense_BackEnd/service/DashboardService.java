package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final TransactionRepo transactionRepo;

    public DashboardService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    //Give month then it will get for the month, else it will get for the year
    public List<Transaction> getTop5TransactionsByAmountForMonthOrYear(Integer userId, Integer month, Integer year,
                                                                       Pageable page){
        LocalDate startDate;
        LocalDate endDate;

        if(month != null){
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.plusMonths(1);
        } else{
            startDate = LocalDate.of(year, 1, 1);
            endDate = startDate.plusYears(1);
        }
        return transactionRepo.findTopFiveByAmount(userId, startDate, endDate, PageRequest.of(0,5));
    }



}
