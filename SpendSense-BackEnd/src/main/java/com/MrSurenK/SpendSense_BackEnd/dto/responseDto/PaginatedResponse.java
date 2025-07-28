package com.MrSurenK.SpendSense_BackEnd.dto.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private boolean isSuccess;
    private String message;
    private List<T> content;
    private int page;
    private int size;
    private long total;
    private int totalPages;
    private boolean first;
    private boolean last;
}