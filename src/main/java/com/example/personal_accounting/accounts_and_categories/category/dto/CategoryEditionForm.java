package com.example.personal_accounting.accounts_and_categories.category.dto;

import jakarta.validation.constraints.Size;

public class CategoryEditionForm {
    public long id;

    @Size(min = 4, max = 40)
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}