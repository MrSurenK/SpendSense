package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigInteger;
import java.sql.Blob;

@StaticMetamodel(Category.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Category_ {

	public static final String CAT_ID = "catId";
	public static final String TITLE = "title";
	public static final String TRANSACTIONS = "transactions";
	public static final String CAT_IMAGE = "catImage";

	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Category#catId
	 **/
	public static volatile SingularAttribute<Category, BigInteger> catId;
	
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
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.Category#catImage
	 **/
	public static volatile SingularAttribute<Category, Blob> catImage;

}

