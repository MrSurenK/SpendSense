package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;

@StaticMetamodel(UserAccount.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class UserAccount_ {

	public static final String LAST_NAME = "lastName";
	public static final String LAST_LOGIN = "lastLogin";
	public static final String OCCUPATION = "occupation";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String TRANSACTIONS = "transactions";
	public static final String FIRST_NAME = "firstName";
	public static final String PASSWORD = "password";
	public static final String DISPLAY_PIC = "displayPic";
	public static final String ID = "id";
	public static final String CATEGORIES = "categories";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#lastName
	 **/
	public static volatile SingularAttribute<UserAccount, String> lastName;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#lastLogin
	 **/
	public static volatile SingularAttribute<UserAccount, LocalDateTime> lastLogin;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#occupation
	 **/
	public static volatile SingularAttribute<UserAccount, String> occupation;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#dateOfBirth
	 **/
	public static volatile SingularAttribute<UserAccount, LocalDate> dateOfBirth;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#transactions
	 **/
	public static volatile SetAttribute<UserAccount, Transaction> transactions;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#firstName
	 **/
	public static volatile SingularAttribute<UserAccount, String> firstName;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#password
	 **/
	public static volatile SingularAttribute<UserAccount, String> password;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#displayPic
	 **/
	public static volatile SingularAttribute<UserAccount, Blob> displayPic;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#id
	 **/
	public static volatile SingularAttribute<UserAccount, Integer> id;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#categories
	 **/
	public static volatile ListAttribute<UserAccount, Category> categories;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount
	 **/
	public static volatile EntityType<UserAccount> class_;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#email
	 **/
	public static volatile SingularAttribute<UserAccount, String> email;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.UserAccount#username
	 **/
	public static volatile SingularAttribute<UserAccount, String> username;

}

