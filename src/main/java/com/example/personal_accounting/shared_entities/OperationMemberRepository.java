package com.example.personal_accounting.shared_entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationMemberRepository extends CrudRepository<OperationMember, Long> {
}
