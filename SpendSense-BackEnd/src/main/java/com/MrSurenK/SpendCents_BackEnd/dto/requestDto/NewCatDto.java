package com.MrSurenK.SpendCents_BackEnd.dto.requestDto;

import com.MrSurenK.SpendCents_BackEnd.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewCatDto {

    @NotNull
    private String name;

    @NotNull
    private TransactionType transactionType;

}
