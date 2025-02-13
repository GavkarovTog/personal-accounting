package com.example.personalaccounting.dataobjects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.personalaccounting.entities.Account;
import com.example.personalaccounting.entities.Category;

import jakarta.validation.constraints.PastOrPresent;

public class OperationFilters {
    public enum OperationType {
        Expense, Income, Transfer, OrphanedTransfer
    }

    private List<OperationType> operationTypes = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate from;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate to;

    public void setOperationTypes(List<OperationType> operationTypes) {
        this.operationTypes = operationTypes;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public List<OperationType> getOperationTypes() {
        return operationTypes;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
    
    public List<Category> getCategories() {
        return categories;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
