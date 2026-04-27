package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ApiResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ChartJsLineChartResponseDTO;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ChartJsPieResponseDTO;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ExpenseBreakdownDTO;
import com.MrSurenK.SpendSense_BackEnd.service.ChartDataService;
import com.MrSurenK.SpendSense_BackEnd.service.SecurityContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chart")
public class ChartingController {

    private final ChartDataService chartDataService;
    private final SecurityContextService securityContextService;

    public ChartingController(ChartDataService chartDataService, SecurityContextService securityContextService) {
        this.chartDataService = chartDataService;
        this.securityContextService = securityContextService;
    }

    @GetMapping("/pie/{month}/{year}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<ChartJsPieResponseDTO>> getChartData(@PathVariable int month, @PathVariable int year) {
        if (month < 1 || month > 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Month must be between 1 and 12");
        }

        if (year < 1900 || year > 3000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Year is out of accepted range");
        }

        int userId = securityContextService.getUserFromSecurityContext().getId();
        log.info("Fetching pie chart data for userId={}, month={}, year={}", userId, month, year);

        List<ExpenseBreakdownDTO> breakdown = chartDataService.getPieChartData(userId, month, year);

        ChartJsPieResponseDTO chartData = new ChartJsPieResponseDTO(
                breakdown.stream().map(ExpenseBreakdownDTO::category).toList(),
                breakdown.stream().map(ExpenseBreakdownDTO::amount).toList(),
                breakdown.stream().map(ExpenseBreakdownDTO::percentage).toList()
        );

        ApiResponse<ChartJsPieResponseDTO> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Pie chart data loaded successfully");
        response.setData(chartData);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/line/{year}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<ChartJsLineChartResponseDTO>> getYearlyLineChart(@PathVariable int year) {
        log.info("Line chart API called with year={}", year);
        if (year < 0 || year > 3000) {
            log.warn("Line chart request rejected due to invalid year={}", year);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Year is out of accepted range");
        }

        int userId = securityContextService.getUserFromSecurityContext().getId();
        long startMs = System.currentTimeMillis();

        try {
            ChartJsLineChartResponseDTO chartData = chartDataService.getLineChartData(userId, year);

            ApiResponse<ChartJsLineChartResponseDTO> response = new ApiResponse<>();
            response.setSuccess(true);
            response.setMessage("Yearly line chart data loaded successfully");
            response.setData(chartData);

            long durationMs = System.currentTimeMillis() - startMs;
            log.info("Line chart API success for userId={}, year={}, labels={}, datasets={}, durationMs={}",
                    userId, year, chartData.labels().size(), chartData.datasets().size(), durationMs);

            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            long durationMs = System.currentTimeMillis() - startMs;
            log.error("Line chart API failed for userId={}, year={}, durationMs={}", userId, year, durationMs, ex);
            throw ex;
        }
    }
}
