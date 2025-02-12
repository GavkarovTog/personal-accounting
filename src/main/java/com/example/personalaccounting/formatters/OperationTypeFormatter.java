package com.example.personalaccounting.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.example.personalaccounting.dataobjects.OperationFilters.OperationType;

public class OperationTypeFormatter implements Formatter<OperationType> {
    @Override
    public String print(OperationType type, Locale locale) {
        return type.toString();
    }

    @Override
    public OperationType parse(String type, Locale locale) throws ParseException {
        return OperationType.valueOf(type);
    }
    
}
