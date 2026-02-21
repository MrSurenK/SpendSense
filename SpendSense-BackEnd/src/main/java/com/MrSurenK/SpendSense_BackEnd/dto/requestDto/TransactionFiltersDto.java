package com.MrSurenK.SpendSense_BackEnd.dto.requestDto;


import com.MrSurenK.SpendSense_BackEnd.model.TransactionType;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction_;
import lombok.Data;
import org.springframework.data.domain.Sort;

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
    private String keyword;

    //Pagination Fields -- defaults given if no pagination info passed from front end
    private Integer page =0;
    private Integer size = 10;
    private String sortField = "lastUpdated";
    private Sort.Direction sortDirection = Sort.Direction.DESC;
}
