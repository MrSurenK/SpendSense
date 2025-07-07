package com.MrSurenK.SpendSense_BackEnd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;


/*
(Enhancement)
ToDo: Allow user to have more than one session logged in (limited to 5 devices) and choose which device to log out on.
*/

@Data
@Entity
public class RefreshToken {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name= "user_id") //Hibernate automatically joins on UserAccount id so no need to specify referenced Column Name
    private UserAccount userAccount;

    @Column(nullable = false,unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private Instant expiryDate;
}
