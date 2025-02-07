package com.example.personalaccounting.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.personalaccounting.entities.Operation;

public interface OperationRepository extends CrudRepository<Operation, Long> {
    public List<Operation> findAll();
    public Optional<Operation> findById(Long id);
    public List<Operation> findAllByOrderByIdDescDateMadeDesc();
}
