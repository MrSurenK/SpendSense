package com.MrSurenK.SpendSense_BackEnd.util;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.TransactionRequestDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.TransactionResponse;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;

public class TransactionMapper {

    public static TransactionResponse mapEntityToTransactionResponseDto(Transaction entity){
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(entity.getId());
        transactionResponse.setTransactionDate(entity.getTransactionDate());
        transactionResponse.setRecurring(entity.getRecurring());
        transactionResponse.setRemarks(entity.getRemarks());
        transactionResponse.setAmount(entity.getAmount());
        transactionResponse.setLastUpdated(entity.getLastUpdated());
        Category cat = entity.getCategory();
         if (cat != null) {
            transactionResponse.setCatId(cat.getId());
            transactionResponse.setCatName(cat.getName());
            transactionResponse.setTransactionType(cat.getTransactionType());
        }

        return transactionResponse;
    }

}
