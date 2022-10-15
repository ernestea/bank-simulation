package com.codefoe.controller;

import com.codefoe.enums.AccountType;
import com.codefoe.model.Account;
import com.codefoe.model.AccountStatus;
import com.codefoe.service.impl.AccountServiceImpl;
import jdk.jfr.ContentType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/account")
public class AccountController {
    AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String accountHome(Model model) {
        model.addAttribute("accounts", accountService.listAllAccounts());
        return "account/index";
    }

    @GetMapping("/create-account")
    public String createAccountForm(Model model) {
        model.addAttribute("account", Account.builder().build());
        model.addAttribute("account_types", AccountType.values());
        model.addAttribute("accountStatuses", AccountStatus.values());
        return "account/create-account";
    }

    @PostMapping("/createAccount")
    public String createAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("account_types", AccountType.values());
            model.addAttribute("accountStatuses", AccountStatus.values());
            return "account/create-account";
        }
        accountService.createNewAccount(account);
        return accountHome(model);
    }
}
