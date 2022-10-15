package com.codefoe.service.impl;

import com.codefoe.enums.AccountType;
import com.codefoe.model.Account;
import com.codefoe.model.AccountStatus;
import com.codefoe.repository.AccountRepository;
import com.codefoe.service.AccountService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .creationDate(creationDate)
                .accountType(accountType)
                .balance(balance)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
        return accountRepository.save(account);

    }

    @Override
    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createNewAccount(Account account) {
        return accountRepository.save(account);
    }
}
