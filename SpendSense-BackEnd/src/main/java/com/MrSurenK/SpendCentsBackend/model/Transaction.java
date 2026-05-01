package com.MrSurenK.SpendCentsBackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name="user_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Transaction parentTransaction; //null for templates

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String title;

    private String remarks;

    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean recurring;//Default value of false in new instances

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate transactionDate;

    private LocalDate nextDueDate; //if recurring is true this will help with the implementation of cron job

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @ManyToOne(fetch=LAZY)
    private UserAccount userAccount;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch=LAZY)
    private Category category;
}
