package com.codefoe;

import com.codefoe.enums.AccountType;
import com.codefoe.model.Account;
import com.codefoe.model.Transaction;
import com.codefoe.service.AccountService;
import com.codefoe.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@SpringBootApplication
public class BankSimulationApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BankSimulationApplication.class, args);
        AccountService accountService = context.getBean(AccountService.class);

        Account sender = accountService.createNewAccount(new BigDecimal("100"),Date.from(Instant.now()), AccountType.CHECKING, 10090L);
        Account receiver = accountService.createNewAccount(new BigDecimal("1"),Date.from(Instant.now()), AccountType.CHECKING, 10091L);

        System.out.println("Sender's balance before transaction " + sender.getBalance());
        System.out.println("Receiver's balance before transaction " + receiver.getBalance());


        TransactionService transactionService = context.getBean(TransactionService.class);
        Transaction transaction = transactionService.makeTransfer(sender,receiver,new BigDecimal("1"),Date.from(Instant.now()),"Checking to checking");
        System.out.println("Amount that was transferred " + transaction.getAmount());
        System.out.println("Sender's balance after transaction " + sender.getBalance());
        System.out.println("Receiver's balance after transaction " + receiver.getBalance());
        transactionService.findAllTransaction().forEach(System.out::println);
    }

}
