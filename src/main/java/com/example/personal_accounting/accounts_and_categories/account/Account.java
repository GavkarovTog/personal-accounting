package com.example.personal_accounting.accounts_and_categories.account;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.personal_accounting.shared_entities.OperationMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "account")
@PrimaryKeyJoinColumn(name = "account_id")
public class Account extends OperationMember {
    private String name;
    
    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Override
    public void addToBalance(BigDecimal additive) {
        currentBalance = currentBalance.add(additive);
    }

    @Override
    public void subtractFromBalance(BigDecimal subtractive) {
        currentBalance = currentBalance.subtract(subtractive);
    }

    @Override
    public BigDecimal getBalance() {
        return currentBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
}
