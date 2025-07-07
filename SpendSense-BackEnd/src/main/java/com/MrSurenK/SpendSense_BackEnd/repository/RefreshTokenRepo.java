package com.MrSurenK.SpendSense_BackEnd.repository;

import com.MrSurenK.SpendSense_BackEnd.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token); //Optional object because token might not exist
}
