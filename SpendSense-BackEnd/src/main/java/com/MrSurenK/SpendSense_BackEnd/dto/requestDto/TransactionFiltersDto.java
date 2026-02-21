package com.MrSurenK.SpendSense_BackEnd.dto.requestDto;


import com.MrSurenK.SpendSense_BackEnd.model.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionFiltersDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal min;
    private BigDecimal max;
    private Integer catId;
    private TransactionType transactionType;
    private Boolean isRecurring;
    private String title;
}
