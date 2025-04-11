package com.MrSurenK.SpendSense_BackEnd.repository;


import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount,Integer> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String userName);

}
