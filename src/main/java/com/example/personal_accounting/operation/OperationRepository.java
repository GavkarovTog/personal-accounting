package com.example.personal_accounting.operation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.example.personal_accounting.user_specific.UserNumber;

public interface OperationRepository extends ListCrudRepository<Operation, Long> {
    public List<Operation> findAllByUserNumberOrderByDateDescIdDesc(UserNumber userNumber);

    public Optional<Operation> findByIdAndUserNumber(Long id, UserNumber userNumber);

    public void deleteByIdAndUserNumber(Long id, UserNumber userNumber);
}
