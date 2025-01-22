package com.example.personalaccounting.repositories;


import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.personalaccounting.entities.Operation;

public interface OperationRepository extends Repository<Long, Operation> {
    public List<Operation> findAll();
}
