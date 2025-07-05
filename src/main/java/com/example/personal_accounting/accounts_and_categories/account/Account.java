package com.example.personal_accounting.accounts_and_categories.account;

import java.math.BigDecimal;

import com.example.personal_accounting.shared_entities.OperationMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
@PrimaryKeyJoinColumn(name = "account_id")
public class Account extends OperationMember {
    private String name;
    
    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Override
    public void addToBalance(BigDecimal additive) {
        currentBalance.add(additive);
    }

    @Override
    public void subtractFromBalance(BigDecimal subtractive) {
        currentBalance.add(subtractive);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
