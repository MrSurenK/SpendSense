package com.MrSurenK.SpendCentsBackend.dto.responseDto;

import java.math.BigDecimal;

public record ExpenseBreakdownDTO(
    String category,
    BigDecimal amount,
    BigDecimal percentage
) {
}
