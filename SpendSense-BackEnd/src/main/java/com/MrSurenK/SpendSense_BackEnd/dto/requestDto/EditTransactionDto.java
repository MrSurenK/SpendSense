package com.MrSurenK.SpendSense_BackEnd.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
