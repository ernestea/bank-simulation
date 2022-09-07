package com.codefoe.service;

import com.codefoe.enums.AccountType;
import com.codefoe.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {
    Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

    List<Account> listAllAccounts();

}
