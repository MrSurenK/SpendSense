package com.MrSurenK.SpendCents_BackEnd.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EditTransactionDto {

    private BigDecimal amount;

    private String title;

    private String remarks;

    private Boolean recurring;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    private Long categoryId;
}
