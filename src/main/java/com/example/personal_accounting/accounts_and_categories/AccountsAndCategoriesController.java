package com.example.personal_accounting.accounts_and_categories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.personal_accounting.settings.UserSettingsService;

@Controller
public class AccountsAndCategoriesController {
    @Autowired
    private UserSettingsService userSettingsService;

    @GetMapping("/accounts-and-categories")
    public String accountsAndCategories(Model model) {
        model.addAttribute("username", userSettingsService.getCurrentUsername());

        return "accounts-and-categories";
    }

    @ModelAttribute("accounts")
    public List<Account> accounts() {
        return List.of();
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
