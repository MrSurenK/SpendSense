package com.MrSurenK.SpendCents_BackEnd.dto.requestDto;

import com.MrSurenK.SpendCents_BackEnd.model.TransactionType;
import lombok.Data;

//Only allow user to change name and transactionType
@Data
public class EditCatDto {

    private String name;

    private TransactionType transactionType;
}
