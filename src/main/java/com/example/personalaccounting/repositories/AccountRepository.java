package com.example.personalaccounting.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.example.personalaccounting.entities.Account;

public interface AccountRepository extends Repository<Account, Long> {
    public List<Account> findAll();

    public Optional<Account> findById(Long id);

    public Account save(Account account);
}
