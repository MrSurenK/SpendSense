package com.MrSurenK.SpendCents_BackEnd.dto.responseDto;

import java.util.List;

public record ChartJsLineChartResponseDTO(
        List<String> labels,
        List<ChartJsLineResponseDTO> datasets
) {
}

