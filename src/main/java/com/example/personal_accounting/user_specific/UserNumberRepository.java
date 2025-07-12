package com.example.personal_accounting.user_specific;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserNumberRepository extends CrudRepository<UserNumber, Long> {
    public Optional<UserNumber> findByUsername(String username);
}
