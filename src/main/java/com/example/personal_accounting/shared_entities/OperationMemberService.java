package com.example.personal_accounting.shared_entities;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationMemberService {
    @Autowired
    private OperationMemberRepository operationMemberRepository;

    public Optional<OperationMember> getOperationMember(long id) {
        return operationMemberRepository.findById(id);
    }
}
