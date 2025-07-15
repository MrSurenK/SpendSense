package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;


    @Column(nullable = false)
    private BigDecimal amount;

    private String remarks;

    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean recurring;//Default value of false in new instances


    @Column(nullable = false)
    private LocalDate transactionDate;


    @ManyToOne(fetch=LAZY)
    private UserAccount userAccount;


    @Column(nullable = false)
    @ManyToOne(fetch=LAZY)
    private Category category;

}
