package com.MrSurenK.SpendSense_BackEnd.dto.responseDto;

import java.math.BigDecimal;
import java.util.List;

public record ChartJsPieResponseDTO(
        List<String> labels,
        List<BigDecimal> values,
        List<BigDecimal> percentages
) {
}

