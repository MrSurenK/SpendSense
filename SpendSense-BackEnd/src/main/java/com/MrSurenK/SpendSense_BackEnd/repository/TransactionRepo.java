package com.MrSurenK.SpendSense_BackEnd.repository;

import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

    Page<Transaction> findByUserAccountId(Integer userId, Pageable page);
    Page<Transaction>findByCategoryIdAndUserAccountId(BigInteger catId, Integer userId);
    Page<Transaction> findByUserAccountIdAndTransactionDateBetween(LocalDate startDate, LocalDate endDate,
                                                                   Integer userId, Pageable page);

}
