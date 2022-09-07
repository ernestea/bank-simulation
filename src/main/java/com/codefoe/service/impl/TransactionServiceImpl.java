package com.codefoe.service.impl;

import com.codefoe.enums.AccountType;
import com.codefoe.exception.AccountOwnershipException;
import com.codefoe.exception.BadRequestException;
import com.codefoe.model.Account;
import com.codefoe.model.Transaction;
import com.codefoe.repository.AccountRepository;
import com.codefoe.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {
    AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {

        validateAccount(sender, receiver);
        checkAccountOwnership(sender, receiver);

        return null;
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
        return null;
    }
}
