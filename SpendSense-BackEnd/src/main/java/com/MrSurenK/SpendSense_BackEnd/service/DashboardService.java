package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.UserTakeHomeIncomeDto;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final TransactionRepo transactionRepo;

    private final UserAccountRepo userAccountRepo;

    public DashboardService(TransactionRepo transactionRepo, UserAccountRepo userAccountRepo) {
        this.transactionRepo = transactionRepo;
        this.userAccountRepo = userAccountRepo;
    }

    //Give month then it will get for the month, else it will get for the year
    public List<Transaction> getTop5TransactionsByAmountForMonthOrYear(Integer userId, Integer month, Integer year
                                                                    ){
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

    public List<Transaction>getAllRecurringSpend(Integer userId, Pageable pageInfo){
        return transactionRepo.allRecurringSpend(userId, pageInfo);
    }

    public UserTakeHomeIncomeDto getTakeHomeSalary(Integer userId, LocalDate startDate, LocalDate endDate){
        UserTakeHomeIncomeDto userIncomeInfo = new UserTakeHomeIncomeDto();
        BigDecimal salary = transactionRepo.findAllSalaryForMth(userId, startDate, endDate);
        BigDecimal bonuses = transactionRepo.findSalaryBonusForMth(userId,startDate,endDate);
        BigDecimal totalIncome = salary.add(bonuses);

        BigDecimal takeHome = BigDecimal.ZERO;
        BigDecimal cpfContribution = BigDecimal.ZERO;
        BigDecimal cpfSalaryCap = new BigDecimal(6800);

        if(totalIncome.compareTo(cpfSalaryCap) <= 0){
            takeHome = (totalIncome.multiply(new BigDecimal("0.8")));
            cpfContribution = totalIncome.subtract(takeHome);
        }else if(totalIncome.compareTo(cpfSalaryCap)> 0) {
            cpfContribution = cpfSalaryCap.multiply(new BigDecimal("0.2"));
            takeHome = totalIncome.subtract(cpfContribution);
        }

        userIncomeInfo.setTakeHomePay(takeHome);
        userIncomeInfo.setSalary(salary);
        userIncomeInfo.setBonus(bonuses);
        userIncomeInfo.setCpf_contribution(cpfContribution);
        return userIncomeInfo;
    }



}
