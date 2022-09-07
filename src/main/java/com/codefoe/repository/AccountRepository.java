package com.codefoe.repository;

import com.codefoe.exception.BadRequestException;
import com.codefoe.exception.RecordNotFoundException;
import com.codefoe.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AccountRepository {
    public static List<Account> accountList = new ArrayList<>();

    public Account save(Account account) {
        accountList.add(account);
        return account;
    }

    public List<Account> findAll() {
        return accountList;
    }

    public Account findById(UUID id) {
        return accountList.stream()
                .filter(account -> account.getId().equals(id))
                .findAny().orElseThrow(() -> new RecordNotFoundException("Account is not listed in the DataBase"));
    }
}
