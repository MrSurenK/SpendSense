package com.MrSurenK.SpendCentsBackend.dto.responseDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserTakeHomeIncomeDto {

    BigDecimal salary;

    BigDecimal bonus;

    BigDecimal cpf_contribution;

    BigDecimal takeHomePay;
}
