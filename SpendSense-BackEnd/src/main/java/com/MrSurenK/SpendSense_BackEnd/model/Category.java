package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.Set;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue
    private BigInteger catId;

    @Basic(optional = false)
    private String title;

    private Blob catImage;

    @OneToMany(mappedBy = Transaction_.CATEGORY)
    private Set<Transaction> transactions;

}
