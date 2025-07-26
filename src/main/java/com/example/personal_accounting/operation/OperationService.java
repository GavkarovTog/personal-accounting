package com.example.personal_accounting.operation;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.personal_accounting.shared_entities.OperationMember;
import com.example.personal_accounting.shared_entities.OperationMemberService;
import com.example.personal_accounting.user_specific.UserNumberService;

import jakarta.transaction.Transactional;

@Service
public class OperationService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private OperationMemberService operationMemberService;

    @Autowired
    private UserNumberService userNumberService;

    @Transactional
    public void createOperation(long fromId, long toId, BigDecimal amount, LocalDate date) {
        OperationMember from = operationMemberService.getOperationMember(fromId)
            .orElseThrow();

        OperationMember to = operationMemberService.getOperationMember(toId)
            .orElseThrow();

        from.subtractFromBalance(amount);
        to.addToBalance(amount);

        Operation operation = new Operation();
        operation.setSource(from);
        operation.setDestination(to);

        operation.setBalanceChange(amount);
        operation.setDate(date);

        operation.setUserNumber(userNumberService.getCurrentUserNumber());

        operationRepository.save(operation);
    }
}
