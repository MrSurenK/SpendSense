package com.MrSurenK.SpendCentsBackend.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Category.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Category_ {

	public static final String IS_SYSTEM = "isSystem";
	public static final String TRANSACTION_TYPE = "transactionType";
	public static final String IS_DELETED = "isDeleted";
	public static final String USER_ACCOUNT = "userAccount";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String TRANSACTIONS = "transactions";

	
	/**
	 * @see com.MrSurenK.SpendCentsBackend.model.Category#isSystem
	 **/
	public static volatile SingularAttribute<Category, Boolean> isSystem;
	
	/**
	 * @see com.MrSurenK.SpendCentsBackend.model.Category#transactionType
	 **/
	public static volatile SingularAttribute<Category, TransactionType> transactionType;
	
	/**
	 * @see com.MrSurenK.SpendCentsBackend.model.Category#isDeleted
	 **/
	public static volatile SingularAttribute<Category, Boolean> isDeleted;
	
	/**
	 * @see com.MrSurenK.SpendCentsBackend.model.Category#userAccount
	 **/
	public static volatile SingularAttribute<Category, UserAccount> userAccount;
	
	/**
	 * @see com.MrSurenK.SpendCentsBackend.model.Category#name
	 **/
	public static volatile SingularAttribute<Category, String> name;
	
	/**
	 * @see com.MrSurenK.SpendCentsBackend.model.Category#id
	 **/
	public static volatile SingularAttribute<Category, Long> id;
	
	/**
	 * @see com.MrSurenK.SpendCentsBackend.model.Category#transactions
	 **/
	public static volatile SetAttribute<Category, Transaction> transactions;
	
	/**
	 * @see com.MrSurenK.SpendCentsBackend.model.Category
	 **/
	public static volatile EntityType<Category> class_;

}

