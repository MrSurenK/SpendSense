package com.MrSurenK.SpendSense_BackEnd.dto.responseDto;

import java.math.BigDecimal;
import java.util.List;

public record ChartJsLineResponseDTO(
        String label,
        List<BigDecimal> data,
        boolean fill,
        String borderColor,
        double tension
) { }
