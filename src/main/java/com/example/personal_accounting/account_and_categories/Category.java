package com.example.personal_accounting.account_and_categories;

import java.math.BigDecimal;

import com.example.personal_accounting.shared_entities.OperationMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends OperationMember {
    private enum CategoryType {
        Expense, Income
    };
    
    private String name;

    @Column(name = "category_type")
    private CategoryType categoryType;

    @Override
    public void addToBalance(BigDecimal additive) {
    
    }

    @Override
    public void subtractFromBalance(BigDecimal subtractive) {
    
    }
}
