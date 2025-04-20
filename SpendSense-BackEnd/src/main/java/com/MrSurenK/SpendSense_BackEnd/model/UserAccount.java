package com.MrSurenK.SpendSense_BackEnd.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Data
@Entity
public class UserAccount extends UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Basic(optional = false)
	@Column(unique = true)
	private String email;

	@Basic(optional = false)
	@Column(unique = true)
	private String username; // Must be unique in db

	@Basic(optional = false)
	private String firstName;

	@Basic(optional = false)
	private String lastName;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dateOfBirth;

	@Basic(optional = false)
	private String password; // Encrypt password before storing in entity

	@UpdateTimestamp
	private LocalDateTime lastLogin;

	@OneToMany(mappedBy = Transaction_.USER_ACCOUNT)
	Set<Transaction> transactions;


}
