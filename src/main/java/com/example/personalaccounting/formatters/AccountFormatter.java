package com.example.personalaccounting.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.example.personalaccounting.entities.Account;
import com.example.personalaccounting.repositories.AccountRepository;

public class AccountFormatter implements Formatter<Account> {
    private AccountRepository accountRepository;

    @Override
    public String print(Account account, Locale locale) {
        System.out.println("IN ACFORMAT");
        return String.valueOf(account.getId());
    }

    @Override
    public Account parse(String accountId, Locale locale) throws ParseException {
        return accountRepository.findById(Long.parseLong(accountId)).orElseThrow();
    }
}