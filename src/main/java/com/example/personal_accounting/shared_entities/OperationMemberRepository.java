package com.example.personal_accounting.shared_entities;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.personal_accounting.user_specific.UserNumber;

@Repository
public interface OperationMemberRepository extends CrudRepository<OperationMember, Long> {
    public Optional<OperationMember> findByIdAndUserNumber(Long id, UserNumber userNumber);
}
