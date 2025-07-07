package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;

@StaticMetamodel(RefreshToken.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class RefreshToken_ {

	public static final String EXPIRY_DATE = "expiryDate";
	public static final String USER_ACCOUNT = "userAccount";
	public static final String ID = "id";
	public static final String REFRESH_TOKEN = "refreshToken";

	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.RefreshToken#expiryDate
	 **/
	public static volatile SingularAttribute<RefreshToken, Instant> expiryDate;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.RefreshToken#userAccount
	 **/
	public static volatile SingularAttribute<RefreshToken, UserAccount> userAccount;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.RefreshToken#id
	 **/
	public static volatile SingularAttribute<RefreshToken, Long> id;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.RefreshToken
	 **/
	public static volatile EntityType<RefreshToken> class_;
	
	/**
	 * @see com.MrSurenK.SpendSense_BackEnd.model.RefreshToken#refreshToken
	 **/
	public static volatile SingularAttribute<RefreshToken, String> refreshToken;

}

