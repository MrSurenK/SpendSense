package com.MrSurenK.SpendSense_BackEnd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransactionType {
    @JsonProperty("expense")
    EXPENSE,

    @JsonProperty("income")
    INCOME
}
