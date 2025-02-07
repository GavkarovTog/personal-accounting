package com.example.personalaccounting.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.example.personalaccounting.entities.Operation;
import com.example.personalaccounting.repositories.AccountRepository;
import com.example.personalaccounting.repositories.OperationRepository;

@Component
public class OperationFormatter implements Formatter<Operation> {
    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public String print(Operation operation, Locale locale) {
        if (operation.getId() == null) {
            return "A" + operation.getAccount().getId();
        }

        return String.valueOf(operation.getId());
    }

    @Override
    public Operation parse(String operationId, Locale locale) throws ParseException {
        if (operationId.startsWith("A")) {
            return Operation.emptyWithAccount(accountRepository.findById(Long.parseLong(operationId.substring(1))).orElseThrow());
        }

        return operationRepository.findById(Long.parseLong(operationId)).orElseThrow();
    }
}
