package com.MrSurenK.SpendSense_BackEnd.dto;

import lombok.Data;

public record RefreshRequest(
        String expiredAccessToken,
        String refreshToken
) {
}
