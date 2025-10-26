package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@StaticMetamodel(Transaction.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Transaction_ {

	public static final String LAST_UPDATED = "lastUpdated";
	public static final String AMOUNT = "amount";
	public static final String PARENT_TRANSACTION = "parentTransaction";
	public static final String RECURRING = "recurring";
	public static final String NEXT_DUE_DATE = "nextDueDate";
	public static final String USER_ACCOUNT = "userAccount";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String TRANSACTION_DATE = "transactionDate";
	public static final String CATEGORY = "category";
	public static final String REMARKS = "remarks";

	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#lastUpdated
	 **/
	public static volatile SingularAttribute<Transaction, LocalDateTime> lastUpdated;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#amount
	 **/
	public static volatile SingularAttribute<Transaction, BigDecimal> amount;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#parentTransaction
	 **/
	public static volatile SingularAttribute<Transaction, Transaction> parentTransaction;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#recurring
	 **/
	public static volatile SingularAttribute<Transaction, Boolean> recurring;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#nextDueDate
	 **/
	public static volatile SingularAttribute<Transaction, LocalDate> nextDueDate;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#userAccount
	 **/
	public static volatile SingularAttribute<Transaction, UserAccount> userAccount;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#id
	 **/
	public static volatile SingularAttribute<Transaction, UUID> id;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#title
	 **/
	public static volatile SingularAttribute<Transaction, String> title;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#transactionDate
	 **/
	public static volatile SingularAttribute<Transaction, LocalDate> transactionDate;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#category
	 **/
	public static volatile SingularAttribute<Transaction, Category> category;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction
	 **/
	public static volatile EntityType<Transaction> class_;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Transaction#remarks
	 **/
	public static volatile SingularAttribute<Transaction, String> remarks;

}

