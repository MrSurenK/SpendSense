package com.MrSurenK.SpendSense_BackEnd.dto.responseDto;

import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Data
public class NewTransactionResponse {

    private UUID id;

    private BigDecimal amount;

    private String remarks;

    private Boolean recurring;//Default value of false in new instances

    private LocalDate transactionDate;

    private LocalDateTime lastUpdated;

    private String catName;
}
