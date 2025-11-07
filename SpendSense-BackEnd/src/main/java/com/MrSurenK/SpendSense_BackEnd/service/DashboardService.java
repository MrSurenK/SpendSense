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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        BigDecimal salary = transactionRepo.sumAllSalaryForMth(userId, startDate, endDate);
        BigDecimal bonuses = transactionRepo.sumSalaryBonusForMth(userId,startDate,endDate);
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

    //ToDo: API - Sum up all transactions including recurring transactions for the month and return value
    //Allow front end to determine date range for more flexibility. Incase in future wanna have a yearly figure
    public HashMap<String, BigDecimal> netCashflow(Integer userId, LocalDate startDate, LocalDate endDate){

        //Get all the user transactions and sum up within given date range
        BigDecimal totalSpend = transactionRepo.sumTotalSpend(userId, startDate, endDate);
        //Get all the user income and sum up within given date range
        BigDecimal totalInflow = transactionRepo.sumTotalIncome(userId, startDate, endDate);
        //Find the difference between income and spend
        BigDecimal netCashflow = totalInflow.subtract(totalSpend);

        HashMap<String, BigDecimal> data = new HashMap<>();
        data.put("totalOutflow", totalSpend);
        data.put("totalInflow", totalInflow);
        data.put("netCashflow", netCashflow);

        return data;
    }


    //API to return sum of each category spending for the month







}
