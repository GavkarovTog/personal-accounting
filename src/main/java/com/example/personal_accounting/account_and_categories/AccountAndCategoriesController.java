package com.example.personal_accounting.account_and_categories;

import org.springframework.web.bind.annotation.GetMapping;

public class AccountAndCategoriesController {
    @GetMapping("/account-and-categories")
    public String accountAndCategories() {
        return "account-and-categories";
    }
}
