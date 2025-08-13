package com.example.personal_accounting.shared_entities;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.personal_accounting.user_specific.UserNumberService;

@Service
public class OperationMemberService {
    @Autowired
    private OperationMemberRepository operationMemberRepository;

    @Autowired
    private UserNumberService userNumberService;

    public Optional<OperationMember> getOperationMember(long id) {
        return operationMemberRepository.findByIdAndUserNumber(id, userNumberService.getCurrentUserNumber());
    }
}
