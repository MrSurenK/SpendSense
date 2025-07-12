package com.MrSurenK.SpendSense_BackEnd.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Data
@Entity
public class UserAccount implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Basic(optional = false)
	@Column(unique = true, nullable = false)
	private String email;

	@Basic(optional = false)
	@Column(unique = true,nullable = false)
	private String username; // Must be unique in db

	@Basic(optional = false)
	@Column(nullable = false)
	private String firstName;

	@Basic(optional = false)
	@Column(nullable = false)
	private String lastName;

	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@Basic(optional = false)
	@Column(nullable = false)
	private String password; // Encrypt password before storing in entity

	@UpdateTimestamp
	private LocalDateTime lastLogin;

	@OneToMany(mappedBy = Transaction_.USER_ACCOUNT)
	Set<Transaction> transactions; //Will not be shown in table, establishes rls

	@OneToMany(mappedBy = Category_.USER_ACCOUNT, cascade = CascadeType.ALL)
	List<Category> categories; //Will not be shown in table, establishes rls

	@Column(nullable = true)
	private Blob displayPic;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword(){
		return this.password;
	}

	@Override
	public String getUsername(){
		return this.username;
	}

}
