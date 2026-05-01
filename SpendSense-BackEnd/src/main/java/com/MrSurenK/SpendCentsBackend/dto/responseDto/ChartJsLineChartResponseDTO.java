package com.MrSurenK.SpendCentsBackend.dto.responseDto;

import java.util.List;

public record ChartJsLineChartResponseDTO(
        List<String> labels,
        List<ChartJsLineResponseDTO> datasets
) {
}

