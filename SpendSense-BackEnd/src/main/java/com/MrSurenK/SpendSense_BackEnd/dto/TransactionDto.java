package com.MrSurenK.SpendSense_BackEnd.dto;

import com.MrSurenK.SpendSense_BackEnd.model.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDto {

    @NotNull(message = "amount must not be null")
    private BigDecimal amount;

    private String remarks;

    @NotNull(message = "recurring must not be null")
    private Boolean recurring;

    @NotNull(message = "Date must not be null")
    private LocalDate date;

    @NotNull(message = "Category must not be null")
    private Category category;
}
