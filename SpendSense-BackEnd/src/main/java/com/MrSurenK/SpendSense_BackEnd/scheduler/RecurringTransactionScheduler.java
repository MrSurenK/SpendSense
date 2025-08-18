package com.MrSurenK.SpendSense_BackEnd.scheduler;

import com.MrSurenK.SpendSense_BackEnd.service.TransactionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecurringTransactionScheduler {

    private final TransactionService transactionService;

    public RecurringTransactionScheduler(TransactionService transactionService){
        this.transactionService = transactionService;
    }
    //Runs everyday at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void run(){
        transactionService.generateNewRecurringTransactions();
    }
}
