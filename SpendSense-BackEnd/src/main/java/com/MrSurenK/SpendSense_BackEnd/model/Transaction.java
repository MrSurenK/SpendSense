package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private UUID transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Basic(optional = false)
    private BigDecimal amount;

    private String remarks;

    private boolean recurring = false; //Default value of false in new instances

    @Basic(optional = false)
    private LocalDate transactionDate;

    @Basic(optional = false)
    @ManyToOne(fetch=LAZY)
    private UserAccount userAccount;

    @Basic(optional = false)
    @ManyToOne(fetch=LAZY)
    private Category category;

}
