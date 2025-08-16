package com.MrSurenK.SpendSense_BackEnd.util;

import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.CategoryResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.DashTopFiveSpendDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.TransactionResponse;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.Transaction;

public class EntityToDtoMapper {

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

    public static DashTopFiveSpendDto mapEntityToDashTopFiveDto(Transaction entity){
        DashTopFiveSpendDto topFiveSpend = new DashTopFiveSpendDto();
        topFiveSpend.setAmount(entity.getAmount());
        topFiveSpend.setTransactionDate(entity.getTransactionDate());
        topFiveSpend.setDescription(entity.getRemarks());
        topFiveSpend.setCatName(entity.getCategory().getName());
        return topFiveSpend;
    }


    public static CategoryResponse mapEntityToCatResponseDto(Category entity){
        CategoryResponse res = new CategoryResponse();
        res.setId(entity.getId());
        res.setName(entity.getName());
        res.setDeleted(entity.isDeleted());
        res.setSystem(entity.isSystem());
        res.setTransactionType(entity.getTransactionType());
        if(entity.getUserAccount() != null){
            res.setUserName(entity.getUserAccount().getUsername());
        }

        return res;
    }

}
