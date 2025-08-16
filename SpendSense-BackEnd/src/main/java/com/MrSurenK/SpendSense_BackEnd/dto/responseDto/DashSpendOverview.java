package com.MrSurenK.SpendSense_BackEnd.dto.responseDto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DashSpendOverview {


    private BigDecimal amount;

    private String catName;

    private String description;

    private LocalDate transactionDate;

}
