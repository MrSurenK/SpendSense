package com.MrSurenK.SpendCents_BackEnd.dto.responseDto;

import com.MrSurenK.SpendCents_BackEnd.model.TransactionType;
import lombok.Data;

@Data
public class CategoryResponse {

    private Long id;

    private String name;

    private boolean isDeleted;

    private boolean isSystem;

    private TransactionType transactionType;

    private String userName; //Created by user
}
