package com.MrSurenK.SpendSense_BackEnd.service_test;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.UserTakeHomeIncomeDto;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DashboardServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @InjectMocks
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTop5Transactions_ForMonth() {
        // Arrange
        int userId = 1;
        int month = 5;
        int year = 2024;

        List<Transaction> mockTransactions = List.of(new Transaction(), new Transaction());
        when(transactionRepo.findTopFiveByAmount(
                anyInt(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(mockTransactions);

        // Act
        List<Transaction> result = dashboardService.getTop5TransactionsByAmountForMonthOrYear(
                userId, month, year);

        // Assert
        assertThat(result).hasSize(2);
        verify(transactionRepo, times(1))
                .findTopFiveByAmount(eq(userId),
                        eq(LocalDate.of(year, month, 1)),
                        eq(LocalDate.of(year, month, 1).plusMonths(1)),
                        eq(PageRequest.of(0, 5)));
    }

    @Test
    void testGetTop5Transactions_ForYear() {
        // Arrange
        int userId = 1;
        Integer month = null;
        int year = 2024;

        List<Transaction> mockTransactions = List.of(new Transaction());
        when(transactionRepo.findTopFiveByAmount(
                anyInt(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(mockTransactions);

        // Act
        List<Transaction> result = dashboardService.getTop5TransactionsByAmountForMonthOrYear(
                userId, month, year);

        // Assert
        assertThat(result).hasSize(1);
        verify(transactionRepo, times(1))
                .findTopFiveByAmount(eq(userId),
                        eq(LocalDate.of(year, 1, 1)),
                        eq(LocalDate.of(year, 1, 1).plusYears(1)),
                        eq(PageRequest.of(0, 5)));
    }

     @Test
    void testCalcTakeHomeSalaryLessThanCap() {
        Integer userId = 1;
        LocalDate startDate = LocalDate.of(2025, 8, 1);
        LocalDate endDate = LocalDate.of(2025, 8, 31);

        // Mock repository returns
        when(transactionRepo.sumAllSalaryForMth(userId, startDate, endDate))
                .thenReturn(new BigDecimal("5000"));
        when(transactionRepo.sumSalaryBonusForMth(userId, startDate, endDate))
                .thenReturn(new BigDecimal("1000"));

        // Call method
        UserTakeHomeIncomeDto dto = dashboardService.getTakeHomeSalary(userId, startDate, endDate);

        // Assert expected values
        BigDecimal expectedTotal = new BigDecimal("6000");
        assertEquals(expectedTotal.multiply(new BigDecimal("0.8")), dto.getTakeHomePay());
        assertEquals(new BigDecimal("5000"), dto.getSalary());
        assertEquals(new BigDecimal("1000"), dto.getBonus());
        assertEquals(expectedTotal.multiply(new BigDecimal("0.2")), dto.getCpf_contribution());

        // Verify repository calls
        verify(transactionRepo).sumAllSalaryForMth(userId, startDate, endDate);
        verify(transactionRepo).sumSalaryBonusForMth(userId, startDate, endDate);
    }

    @Test
    void testGetTakeHomeSalary_WhenIncomeGreaterThanCpfCap() {
        Integer userId = 1;
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 31);

        // Arrange: salary + bonus = 14,000 (greater than CPF cap 6800)
        BigDecimal salary = new BigDecimal("4000");
        BigDecimal bonus = new BigDecimal("10000");

        when(transactionRepo.sumAllSalaryForMth(userId, startDate, endDate)).thenReturn(salary);
        when(transactionRepo.sumSalaryBonusForMth(userId, startDate, endDate)).thenReturn(bonus);

        // Act
        UserTakeHomeIncomeDto result = dashboardService.getTakeHomeSalary(userId, startDate, endDate);

        // CPF contribution capped at 20% of 6800 = 1360
        BigDecimal expectedCpf = new BigDecimal("1360.00");
        // Take home = 14000 - 1360 = 12640
        BigDecimal expectedTakeHome = new BigDecimal("12640.00");

        // Assert
        assertEquals(salary, result.getSalary());
        assertEquals(bonus, result.getBonus());
        assertEquals(0, expectedCpf.compareTo(result.getCpf_contribution())); // use compareTo for BigDecimal
        assertEquals(0, expectedTakeHome.compareTo(result.getTakeHomePay()));
    }

//    @Test
//    void testCashflowSum(){
//        Integer userId = 1;
//        LocalDate startDate = LocalDate.of(2025, 1, 1);
//        LocalDate endDate = LocalDate.of(2025, 1, 31);
//
//        BigDecimal income = new BigDecimal(1000);
//
//        BigDecimal expense = new BigDecimal(200);
//
//        //Arrange
//        when(transactionRepo.sumTotalIncome(userId, startDate, endDate)).thenReturn(income);
//        when(transactionRepo.sumTotalSpend(userId, startDate, endDate)).thenReturn(expense);
//
//        //Act
//        BigDecimal res = dashboardService.netCashflow(userId,startDate,endDate);
//
//        //Assert
//        assertEquals(BigDecimal.valueOf(800), res);
//    }
}
