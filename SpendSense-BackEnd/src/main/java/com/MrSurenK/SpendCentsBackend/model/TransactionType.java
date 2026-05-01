package com.MrSurenK.SpendCentsBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransactionType {
    @JsonProperty("expense")
    EXPENSE,

    @JsonProperty("income")
    INCOME
}
