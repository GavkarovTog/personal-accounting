package com.example.personal_accounting.accounts_and_categories.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.personal_accounting.user_specific.UserNumber;
import com.example.personal_accounting.user_specific.UserNumberService;

@Service
public class AccountService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserNumberService userNumberService;

    public boolean suchAccountExists(String accountName) {
        return accountRepository.existsByNameAndUserNumber(accountName, userNumberService.getCurrentUserNumber());
    }

    public void createAccount(String name, BigDecimal startBalance) {
        Account account = new Account();
        account.setName(name);
        account.setCurrentBalance(startBalance);

        UserNumber userNumber = userNumberService.getCurrentUserNumber();
        account.setUserNumber(userNumber);

        accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAllByUserNumber(userNumberService.getCurrentUserNumber()).forEach(accounts::add);

        return accounts;
    }

    public Optional<Account> getAccount(long id) {
        return accountRepository.findByIdAndUserNumber(id, userNumberService.getCurrentUserNumber());
    }

    public void renameAccount(long id, String newName) {
        Account account = getAccount(id).orElseThrow();
        account.setName(newName);

        accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(long id) {
        accountRepository.deleteByIdAndUserNumber(id, userNumberService.getCurrentUserNumber());
    }
}