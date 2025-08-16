package com.MrSurenK.SpendSense_BackEnd.service_test;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
}
