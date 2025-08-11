package com.MrSurenK.SpendSense_BackEnd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @Column(columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    private String remarks;

    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean recurring;//Default value of false in new instances

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate transactionDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @ManyToOne(fetch=LAZY)
    private UserAccount userAccount;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch=LAZY)
    private Category category;

}
