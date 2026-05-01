package com.MrSurenK.SpendCentsBackend.dto.requestDto;

import com.MrSurenK.SpendCentsBackend.model.TransactionType;
import lombok.Data;

//Only allow user to change name and transactionType
@Data
public class EditCatDto {

    private String name;

    private TransactionType transactionType;
}
