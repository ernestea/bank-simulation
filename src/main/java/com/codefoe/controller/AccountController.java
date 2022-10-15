package com.codefoe.controller;

import com.codefoe.enums.AccountType;
import com.codefoe.model.Account;
import com.codefoe.model.AccountStatus;
import com.codefoe.service.impl.AccountServiceImpl;
import jdk.jfr.ContentType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Controller

public class AccountController {
    AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService){
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String accountHome(Model model) {
         model.addAttribute("accounts",accountService.listAllAccounts());
        return "account/index";
    }

    @GetMapping("/account/create-account")
    public String createAccountForm(Model model){
        model.addAttribute("newAccount", Account.builder().build());
        model.addAttribute("account_types", AccountType.values());
        model.addAttribute("accountStatuses", AccountStatus.values());
        return "account/create-account";
    }
    @RequestMapping( "/createAccount")
    public String createAccount(Model model, @RequestParam Long userId, BigDecimal balance, AccountStatus accountStatus,AccountType accountType) {
        Account account = Account.builder().userId(userId).balance(balance).accountStatus(accountStatus).id(UUID.randomUUID()).accountType(accountType).build();
        accountService.createNewAccount(account);
        return accountHome(model);
    }
}
