package com.example.personal_accounting.accounts_and_categories.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public boolean suchAccountExists(String accountName) {
        return accountRepository.existsByName(accountName);
    }

    public void createAccount(String name, BigDecimal startBalance) {
        Account account = new Account();
        account.setName(name);
        account.setCurrentBalance(startBalance);

        accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);

        return accounts;
    }

    public Optional<Account> getAccount(long id) {
        return accountRepository.findById(id);
    }

    public void renameAccount(long id, String newName) {
        Account account = getAccount(id).orElseThrow();
        account.setName(newName);

        accountRepository.save(account);
    }

    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }
}
