package com.MrSurenK.SpendSense_BackEnd.service_test;

import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ChartJsLineChartResponseDTO;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.service.ChartDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChartDataServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @InjectMocks
    private ChartDataService chartDataService;

    @Test
    void getLineChartData_currentYear_stopsAtCurrentMonth() {
        int userId = 1;
        int year = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        when(transactionRepo.sumTotalSpend(anyInt(), any(LocalDate.class), any(LocalDate.class)))
                .thenAnswer(invocation -> {
                    LocalDate start = invocation.getArgument(1);
                    return BigDecimal.valueOf(start.getMonthValue() * 10L);
                });

        when(transactionRepo.sumTotalIncome(anyInt(), any(LocalDate.class), any(LocalDate.class)))
                .thenAnswer(invocation -> {
                    LocalDate start = invocation.getArgument(1);
                    return BigDecimal.valueOf(start.getMonthValue() * 20L);
                });

        ChartJsLineChartResponseDTO result = chartDataService.getLineChartData(userId, year);

        assertEquals(currentMonth, result.labels().size());
        assertEquals("Jan", result.labels().getFirst());
        assertEquals(Month.of(currentMonth).getDisplayName(TextStyle.SHORT, Locale.ENGLISH), result.labels().getLast());
        assertEquals(currentMonth, result.datasets().get(0).data().size());
        assertEquals(currentMonth, result.datasets().get(1).data().size());
        assertEquals(BigDecimal.valueOf(10), result.datasets().get(0).data().getFirst());
        assertEquals(BigDecimal.valueOf(currentMonth * 20L), result.datasets().get(1).data().getLast());
    }

    @Test
    void getLineChartData_pastYear_includesAllMonths() {
        int userId = 1;
        int year = LocalDate.now().minusYears(1).getYear();

        when(transactionRepo.sumTotalSpend(anyInt(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(BigDecimal.valueOf(100));

        when(transactionRepo.sumTotalIncome(anyInt(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(BigDecimal.valueOf(250));

        ChartJsLineChartResponseDTO result = chartDataService.getLineChartData(userId, year);

        assertEquals(12, result.labels().size());
        assertEquals("Jan", result.labels().getFirst());
        assertEquals("Dec", result.labels().getLast());
        assertEquals(12, result.datasets().get(0).data().size());
        assertEquals(12, result.datasets().get(1).data().size());
        assertEquals(BigDecimal.valueOf(100), result.datasets().get(0).data().getFirst());
        assertEquals(BigDecimal.valueOf(250), result.datasets().get(1).data().getLast());
    }

    @Test
    void getLineChartData_futureYear_returnsTwelveZeroMonths() {
        int userId = 1;
        int year = LocalDate.now().plusYears(2).getYear();

        when(transactionRepo.sumTotalSpend(anyInt(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(BigDecimal.ZERO);
        when(transactionRepo.sumTotalIncome(anyInt(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(BigDecimal.ZERO);

        ChartJsLineChartResponseDTO result = chartDataService.getLineChartData(userId, year);

        assertEquals(12, result.labels().size());
        assertEquals(12, result.datasets().get(0).data().size());
        assertEquals(12, result.datasets().get(1).data().size());
        assertEquals(BigDecimal.ZERO, result.datasets().get(0).data().getFirst());
        assertEquals(BigDecimal.ZERO, result.datasets().get(1).data().getLast());
    }
}
