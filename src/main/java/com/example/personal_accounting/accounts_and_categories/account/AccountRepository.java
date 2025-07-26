package com.example.personal_accounting.accounts_and_categories.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.personal_accounting.user_specific.UserNumber;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    public boolean existsByNameAndUserNumber(String name, UserNumber userNumber);

    public List<Account> findAllByUserNumberOrderById(UserNumber userNumber);

    public Optional<Account> findByIdAndUserNumber(Long id, UserNumber userNumber);

    public void deleteByIdAndUserNumber(Long id, UserNumber userNumber);
}
