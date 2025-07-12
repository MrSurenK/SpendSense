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

    @Id
    @GeneratedValue
    private BigInteger id;

    @Basic(optional = false)
    private String title;

    @ColumnDefault("false")
    private boolean isDeleted;

    @OneToMany(mappedBy = Transaction_.CATEGORY)
    private Set<Transaction> transactions; //will not be shown in table, establishes rls

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private UserAccount userAccount;


}
