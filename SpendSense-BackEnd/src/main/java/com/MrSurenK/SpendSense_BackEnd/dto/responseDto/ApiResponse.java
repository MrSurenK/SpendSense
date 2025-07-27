package com.MrSurenK.SpendSense_BackEnd.dto.responseDto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
