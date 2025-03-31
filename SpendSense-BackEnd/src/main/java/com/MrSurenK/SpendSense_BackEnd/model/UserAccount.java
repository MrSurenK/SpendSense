package com.MrSurenK.SpendSense_BackEnd.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Basic(optional = false)
	private String email;

	@Basic(optional = false)
	private String userName; // Must be unique in db

	@Basic(optional = false)
	private String firstName;

	@Basic(optional = false)
	private String lastName;

	@Basic(optional = false)
	private LocalDate dob;

	@Basic(optional = false)
	private String password; // Encrypt password before storing in entity

	private LocalDateTime lastLogin;

	@OneToMany(mappedBy = Transaction_.USER_ACCOUNT)
	Set<Transaction> transactions;
}
