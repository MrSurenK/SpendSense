package com.MrSurenK.SpendSense_BackEnd.repository;


import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount,Integer> {


    @Query(value = "SELECT EXISTS(SELECT 1 FROM user_account WHERE username=:username)",nativeQuery = true)
    int existsByUsername(String username);

    boolean existsByEmail(String email);

}
