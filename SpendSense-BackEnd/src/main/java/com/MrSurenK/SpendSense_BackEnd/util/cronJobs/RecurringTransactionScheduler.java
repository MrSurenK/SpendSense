package com.MrSurenK.SpendSense_BackEnd.util.cronJobs;

import com.MrSurenK.SpendSense_BackEnd.model.Transaction;
import com.MrSurenK.SpendSense_BackEnd.repository.TransactionRepo;
import com.MrSurenK.SpendSense_BackEnd.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RecurringTransactionScheduler {

    private final TransactionRepo transactionRepo;

    public RecurringTransactionScheduler(TransactionRepo transactionRepo){
        this.transactionRepo = transactionRepo;
    }


    @Transactional
    @Scheduled(cron="0 0 0 * * *") //Runs everyday on midnight
    public void generateNewRecurringTransactions(){
        LocalDate today = LocalDate.now();

        List<Transaction> recurringTransactions = transactionRepo.findByRecurringTrueAndNextDueDate(today);

        for(Transaction txn: recurringTransactions){

            //ToDo: Create a parent id to link recurring txn back to parent and implement copies in same table

        }

    }



}
