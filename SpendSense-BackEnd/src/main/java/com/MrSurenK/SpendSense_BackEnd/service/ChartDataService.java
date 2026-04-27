package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ChartJsLineChartResponseDTO;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ChartJsLineResponseDTO;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ExpenseBreakdownDTO;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Service to generate data required for charting technologies.
 */

@Service
@Slf4j
public class ChartDataService {

    private final TransactionRepo transactionRepo;

    ChartDataService(TransactionRepo transactionRepo){
        this.transactionRepo = transactionRepo;
    }

    /**
     * Get pie chart data for monthly expense breakdown by category.
     */
    public List<ExpenseBreakdownDTO> getPieChartData(int userId, int mth, int year){
        log.info("Loading pie chart data for userId: {} in month: {} and year: {}", userId, mth, year);

        List<Transaction> transactions = transactionRepo.findAllTransactionsInGivenTimePeriod(userId,mth,year);
        if(transactions.isEmpty()){
            return List.of();
        }

        // Group expense amount by category so each category appears once in the pie chart.
        Map<String, BigDecimal> categoryTotals = new LinkedHashMap<>();
        for (Transaction transaction : transactions) {
            String categoryName = transaction.getCategory().getName();
            categoryTotals.merge(categoryName, transaction.getAmount(), BigDecimal::add);
        }

        BigDecimal grandTotal = categoryTotals.values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<ExpenseBreakdownDTO> data = new ArrayList<>();
        categoryTotals.entrySet()
                .stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .forEach(entry -> {
                    BigDecimal percentage = BigDecimal.ZERO;
                    if (grandTotal.compareTo(BigDecimal.ZERO) > 0) {
                        percentage = entry.getValue()
                                .divide(grandTotal, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100))
                                .setScale(2, RoundingMode.HALF_UP);
                    }

                    data.add(new ExpenseBreakdownDTO(
                            entry.getKey(),
                            entry.getValue(),
                            percentage
                    ));
                });

        log.info("Successfully processed {} categories for pie chart", data.size());
        return data;
    }

    /**
     * Get line chart data with fixed Jan-Dec monthly expense and income totals for a given year.
     */
    public ChartJsLineChartResponseDTO getLineChartData(int userId, int year) {
        List<String> labels = new ArrayList<>();
        List<BigDecimal> expenseData = new ArrayList<>();
        List<BigDecimal> incomeData = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            BigDecimal expense = transactionRepo.sumTotalSpend(userId, startDate, endDate);
            BigDecimal income = transactionRepo.sumTotalIncome(userId, startDate, endDate);

            labels.add(Month.of(month).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            expenseData.add(expense);
            incomeData.add(income);
        }

        List<ChartJsLineResponseDTO> datasets = List.of(
                new ChartJsLineResponseDTO("Expense", expenseData, false, "rgb(255, 99, 132)", 0.25),
                new ChartJsLineResponseDTO("Income", incomeData, false, "rgb(75, 192, 192)", 0.25)
        );

        return new ChartJsLineChartResponseDTO(labels, datasets);
    }
}
