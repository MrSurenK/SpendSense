package com.MrSurenK.SpendCents_BackEnd.dto.responseDto;

import java.math.BigDecimal;

public record ExpenseBreakdownDTO(
    String category,
    BigDecimal amount,
    BigDecimal percentage
) {
}
