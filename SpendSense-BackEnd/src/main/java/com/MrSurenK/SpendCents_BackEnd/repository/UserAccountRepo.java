package com.MrSurenK.SpendCents_BackEnd.repository;


import com.MrSurenK.SpendCents_BackEnd.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount,Integer> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String userName);
    Optional<UserAccount> findByUsername(String username);
}
