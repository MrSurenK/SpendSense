package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Blob;
import java.util.Set;

@Data
@Entity
public class Categories {

    @Id
    @GeneratedValue
    private BigInteger catId;

    @Basic(optional = false)
    private String title;

    private Blob catImage;

    @OneToMany(mappedBy = Transactions_.CATEGORY)
    private Set<Transactions> transactions;

}
