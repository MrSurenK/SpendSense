package com.MrSurenK.SpendSense_BackEnd.dto.requestDto;

public record RefreshRequest(
        String expiredAccessToken,
        String refreshToken
) {
}
