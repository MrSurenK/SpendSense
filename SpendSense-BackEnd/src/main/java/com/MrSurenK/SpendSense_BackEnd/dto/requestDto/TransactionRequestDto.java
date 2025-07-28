package com.MrSurenK.SpendSense_BackEnd.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequestDto {

    @NotNull(message = "amount must not be null")
    private BigDecimal amount;

    private String remarks;

    @NotNull(message = "recurring must not be null")
    private Boolean recurring;

    @NotNull(message = "Date must not be null")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @NotNull(message = "Category must not be null")
    private Long categoryId;
}
