package com.MrSurenK.SpendSense_BackEnd.dto.responseDto;

import com.MrSurenK.SpendSense_BackEnd.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DashTopFiveSpendDto {


    private BigDecimal amount;

    private String catName;

    private String description;

    private LocalDate transactionDate;

}
