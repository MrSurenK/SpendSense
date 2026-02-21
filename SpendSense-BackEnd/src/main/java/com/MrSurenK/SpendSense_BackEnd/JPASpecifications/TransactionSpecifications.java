package com.MrSurenK.SpendSense_BackEnd.JPASpecifications;

import com.MrSurenK.SpendSense_BackEnd.model.*;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;


public final class TransactionSpecifications {

    /*
    -- Filters for transactions --
    1. Date range
    2. Is it recurring?
    3. Is it expense or income?
    4. Transaction Categories
    5. Amount ranges
    6. Title
    7. Global Search -> title, remarks and amount values in UI search bar
     */

    //Specification to get transactions belonging to the given user id
    public static Specification<Transaction> hasUser(Integer userId){
        if(userId == null) return null; //Skip filter
        return (root, query, cb)->
            cb.equal(root.get("userAccount").get("id"), userId);
    }

    //Specification to get transactions within the given date ranges and if none given then default to current month
   public static Specification<Transaction> withinDateRange(LocalDate startDate, LocalDate endDate){

           LocalDate from = (startDate != null) ? startDate : YearMonth.now().atDay(1);
           LocalDate to = (endDate != null) ? endDate : YearMonth.now().atEndOfMonth();

           return(root, query, cb)->
                   cb.between(root.get("transactionDate"),from,to);
   }

   public static Specification<Transaction> isRecurringFilter(Boolean recurring){
        if(recurring == null ) return null;
        return (root, query, cb)->
                cb.equal(root.get(Transaction_.RECURRING), recurring);
   }

   public static Specification<Transaction> hasExpenseOrIncome(TransactionType transactionType){
        if(transactionType == null ) return null;
        return (root, query, cb) -> {
            return cb.equal(root.get(Transaction_.CATEGORY).get(Category_.TRANSACTION_TYPE), transactionType);
        };
   }

   //Ensure that category spec goes together with hasUser spec to ensure that only user spec is shown
   public static Specification<Transaction> hasCategory(Integer catId){
        if(catId == null) return null;
        return(root, query, cb) -> {
            return cb.equal(root.get(Transaction_.CATEGORY).get(Category_.ID), catId);
        };
   }

   public static Specification<Transaction> hasTitle(String title){
        if(title == null || title.isBlank()) return null;
        return(root, query, cb) -> {
            return cb.like(cb.lower(root.get(Transaction_.TITLE)),
                    "%" + title.toLowerCase() + "%");
        };
   }

   public static Specification<Transaction> amountBetween(BigDecimal min, BigDecimal max){
       return (root, query, cb) -> {
           if(min != null && max != null){
               return cb.between(root.get(Transaction_.AMOUNT), min, max);
           }

           if(min != null){
               return cb.greaterThanOrEqualTo(root.get(Transaction_.AMOUNT), min);
           }

           if(max != null){
               return cb.lessThanOrEqualTo(root.get(Transaction_.AMOUNT), max);
           }
           return null;
       };
   }

   public static Specification<Transaction>globalSearch(String keyword){
        if(keyword == null || keyword.isBlank()) return null;

        String pattern = "%" + keyword.toLowerCase() + "%";

        return(root, query, cb) -> {
                Predicate titleMatch = cb.like(cb.lower(root.get(Transaction_.TITLE)), pattern);
                Predicate descMatch  = cb.like(cb.lower(root.get(Transaction_.REMARKS)), pattern);

                //Try parsing the keyword as a number
                Predicate amountMatch = null;
                try{
                    BigDecimal amt = new BigDecimal(keyword);
                    amountMatch = cb.equal(root.get(Transaction_.AMOUNT), amt);
                } catch (NumberFormatException e){
                    // not a number, so ignore amount match
                }

                if(amountMatch != null){
                    return cb.or(titleMatch, descMatch, amountMatch);
                } else{
                    return cb.or(titleMatch, descMatch);
                }
        };
   }
}
