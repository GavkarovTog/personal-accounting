package com.example.personal_accounting.accounts_and_categories.account;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    public boolean existsByName(String name);
}
