package com.example.personal_accounting.operation;

import org.springframework.data.repository.ListCrudRepository;

public interface OperationRepository extends ListCrudRepository<Operation, Long> {
    
}
