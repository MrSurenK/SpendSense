package com.MrSurenK.SpendSense_BackEnd.dto.requestDto;

import com.MrSurenK.SpendSense_BackEnd.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class NewCatDto {

    @NotNull
    private String name;

    @NotNull
    private TransactionType transactionType;

}
