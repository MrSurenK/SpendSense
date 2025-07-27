package com.MrSurenK.SpendSense_BackEnd.repository;

import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

    Page<Transaction> findAllByUserAccountId(UserAccount userAccount, Pageable page);
    Page<Transaction>findAllByCategoryIdAndUserAccountId(Long catId, UserAccount userAccount, Pageable page);
    Page<Transaction> findAllByUserAccountIdAndTransactionDateBetween(LocalDate startDate, LocalDate endDate,
                                                                      UserAccount userAccount, Pageable page);


}
