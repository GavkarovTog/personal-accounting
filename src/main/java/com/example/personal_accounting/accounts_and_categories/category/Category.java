package com.example.personal_accounting.accounts_and_categories.category;

import java.math.BigDecimal;

import com.example.personal_accounting.shared_entities.OperationMember;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
@PrimaryKeyJoinColumn(name = "category_id")
public class Category extends OperationMember {

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type")
    private CategoryType categoryType;

    @Override
    public void addToBalance(BigDecimal additive) {
    
    }

    @Override
    public void subtractFromBalance(BigDecimal subtractive) {
    
    }

    @Override
    public boolean isAccount() {
        return false;
    }

    @Override
    public boolean isCategory() {
        return true;
    }

    @Override
    public BigDecimal getBalance() {
        return BigDecimal.ZERO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
