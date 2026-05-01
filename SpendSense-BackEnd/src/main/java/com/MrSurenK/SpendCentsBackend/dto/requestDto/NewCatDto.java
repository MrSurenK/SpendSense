package com.MrSurenK.SpendCentsBackend.dto.requestDto;

import com.MrSurenK.SpendCentsBackend.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewCatDto {

    @NotNull
    private String name;

    @NotNull
    private TransactionType transactionType;

}
