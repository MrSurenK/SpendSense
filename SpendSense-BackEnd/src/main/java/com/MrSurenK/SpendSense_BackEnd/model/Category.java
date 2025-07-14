package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.Set;

@Data
@Entity
public class Category {
//ToDo: Add isDefault or isSystem column to indicate default values in table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private String name;

    @ColumnDefault("false")
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @OneToMany(mappedBy = Transaction_.CATEGORY)
    private Set<Transaction> transactions; //will not be shown in table, establishes rls

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserAccount userAccount;

}
