package com.MrSurenK.SpendSense_BackEnd.repository;

import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByUserAccountId(Integer userId);
    List<Transaction>findByCategoryIdAndUserAccountId(BigInteger catId, Integer userId);


}
