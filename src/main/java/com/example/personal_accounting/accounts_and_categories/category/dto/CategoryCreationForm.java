package com.example.personal_accounting.accounts_and_categories.category.dto;

import com.example.personal_accounting.accounts_and_categories.category.CategoryType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryCreationForm {
    @Size(min = 4, max = 40)
    public String name;

    @NotNull
    public CategoryType categoryType;
    
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
