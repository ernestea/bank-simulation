package com.codefoe.service.impl;

import com.codefoe.enums.AccountType;
import com.codefoe.exception.AccountOwnershipException;
import com.codefoe.exception.BadRequestException;
import com.codefoe.exception.BalanceNotSufficientException;
import com.codefoe.exception.SiteUnderConstructionException;
import com.codefoe.model.Account;
import com.codefoe.model.Transaction;
import com.codefoe.repository.AccountRepository;
import com.codefoe.repository.TransactionRepository;
import com.codefoe.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(AccountRepository accountRepository,TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Value("${under_construction}")
    private boolean under_construction;

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        if(under_construction) {
            throw new SiteUnderConstructionException("App is under construction, try again later");

        }else{
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
            Transaction transaction = Transaction.builder()
                    .amount(amount)
                    .sender(sender.getId())
                    .receiver(receiver.getId())
                    .creationDate(creationDate)
                    .message("Transaction created successfully")
                    .build();
            transactionRepository.addTransaction(transaction);
            return transaction;
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if(validateSenderAccountHasAmount(amount,sender)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Insufficient Balance");
        }
    }

    private boolean validateSenderAccountHasAmount(BigDecimal amount,Account sender) {
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(Account sender, Account receiver) {
        /*
        write an if statement that checks if one of the account is saving,
        and if user of sender or receiver is not the same, throw AccountOwnershipException
         */
        if(sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING)){
            if( ! sender.getUserId().equals(receiver.getUserId())){
                throw new AccountOwnershipException("Transactions that involve account type savings can occur only for same user");
            }
        }
    }

    private void validateAccount(Account sender, Account receiver) {
        /*
        -- If any of the accounts is null
        -- If account ids arethe same (same account)
        -- If the account exist in the database(repository)
         */
        if (sender == null || receiver == null) {
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("Sender account needs to be different than receiver account");
        }
        findAccountById(sender.getId());
        findAccountById(receiver.getId());
    }

    private void findAccountById(UUID id) {
        accountRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return TransactionRepository.transactions;
    }
}
