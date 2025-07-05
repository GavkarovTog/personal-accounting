package com.example.personal_accounting.accounts_and_categories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.personal_accounting.accounts_and_categories.account.Account;
import com.example.personal_accounting.accounts_and_categories.account.AccountService;
import com.example.personal_accounting.settings.UserSettingsService;

@Controller
public class AccountsAndCategoriesController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private AccountService accountService;


    @GetMapping("/accounts-and-categories")
    public String accountsAndCategories(Model model) {
        model.addAttribute("username", userSettingsService.getCurrentUsername());

        return "accounts-and-categories";
    }

    @ModelAttribute("currency")
    public String currency() {
        return userSettingsService.getCurrentUserSetup().getCurrency();
    }

    @ModelAttribute("accounts")
    public List<Account> accounts() {
        return accountService.getAllAccounts();
    }

    @ModelAttribute("expenses")
    public List<Category> expenses() {
        return List.of();
    }

    @ModelAttribute("incomes")
    public List<Category> incomes() {
        return List.of();
    }
}
