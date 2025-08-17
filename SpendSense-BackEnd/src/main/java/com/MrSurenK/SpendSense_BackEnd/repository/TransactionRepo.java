package com.MrSurenK.SpendSense_BackEnd.repository;

import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

    Page<Transaction> findAllByUserAccountId(int userId, Pageable page);
    Page<Transaction>findAllByCategoryIdAndUserAccountId(Long catId, int userId, Pageable page);
    Page<Transaction> findAllByUserAccountIdAndTransactionDateBetween(LocalDate startDate, LocalDate endDate,
                                                                      int userId, Pageable page);

    //More efficient than using MONTH and YEAR filters
    @Query(value ="SELECT t FROM Transaction t WHERE (t.userAccount.id = :userId AND t.transactionDate >=:startDate AND t.transactionDate < :endDate AND t.category.transactionType = com.MrSurenK.SpendSense_BackEnd.model.TransactionType.EXPENSE) ORDER BY t.amount DESC")
    List<Transaction> findTopFiveByAmount(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate, Pageable page);

    @Query(value="SELECT t FROM Transaction t WHERE (t.userAccount.id=:userId AND t.category.transactionType = com.MrSurenK.SpendSense_BackEnd.model.TransactionType.EXPENSE AND t.recurring=true)")
    List<Transaction>allRecurringSpend(@Param("userId") Integer userId, Pageable page);

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.userAccount.id=:userId AND t.category.transactionType = com.MrSurenK.SpendSense_BackEnd.model.TransactionType.INCOME AND t.category.name='Bonus' AND t.transactionDate >=:startDate AND t.transactionDate <=:endDate")
    BigDecimal findSalaryBonusForMth(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.userAccount.id=:userId AND t.category.transactionType = com.MrSurenK.SpendSense_BackEnd.model.TransactionType.INCOME AND t.category.name='Salary' AND t.transactionDate >=:startDate AND t.transactionDate <=:endDate")
    BigDecimal findAllSalaryForMth(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);
}
