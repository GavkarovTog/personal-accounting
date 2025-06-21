package com.example.personal_accounting.account_and_categories;

import java.math.BigDecimal;

import com.example.personal_accounting.shared_entities.OperationMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account extends OperationMember {
    private String name;
    
    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Override
    public void addToBalance(BigDecimal additive) {
        throw new UnsupportedOperationException("to implement");
    }

    @Override
    public void subtractFromBalance(BigDecimal subtractive) {
        throw new UnsupportedOperationException("to implement");
    }
}
