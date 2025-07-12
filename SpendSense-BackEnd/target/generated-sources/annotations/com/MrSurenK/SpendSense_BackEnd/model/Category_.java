package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigInteger;

@StaticMetamodel(Category.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Category_ {

	public static final String IS_DELETED = "isDeleted";
	public static final String USER_ACCOUNT = "userAccount";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String TRANSACTIONS = "transactions";

	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Category#isDeleted
	 **/
	public static volatile SingularAttribute<Category, Boolean> isDeleted;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Category#userAccount
	 **/
	public static volatile SingularAttribute<Category, UserAccount> userAccount;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Category#id
	 **/
	public static volatile SingularAttribute<Category, BigInteger> id;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Category#title
	 **/
	public static volatile SingularAttribute<Category, String> title;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Category#transactions
	 **/
	public static volatile SetAttribute<Category, Transaction> transactions;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Category
	 **/
	public static volatile EntityType<Category> class_;

}

