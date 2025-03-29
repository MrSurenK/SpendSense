package com.MrSurenK.SpendSense_BackEnd.repository;


import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepo extends CrudRepository<UserAccount,Integer> {

    UserAccount save(UserAccount userAccount);

    Iterable<UserAccount> findAll();

    @Query(value = "SELECT EXISTS(SELECT 1 FROM user_account WHERE username=:username)",nativeQuery = true)
    int existsByUsername(String username);

}
