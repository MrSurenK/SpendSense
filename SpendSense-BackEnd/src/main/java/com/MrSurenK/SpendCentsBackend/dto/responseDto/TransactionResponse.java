package com.MrSurenK.SpendCentsBackend.dto.responseDto;

import com.MrSurenK.SpendCentsBackend.model.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionResponse {

    private UUID id;

    private BigDecimal amount;

    private String title;

    private String remarks;

    private Boolean recurring;//Default value of false in new instances

    private LocalDate transactionDate;

    private LocalDate nextDueDate;

    private LocalDateTime lastUpdated;

    private Long catId;

    private String catName;

    private TransactionType transactionType;
}
