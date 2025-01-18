package com.example.personalaccounting.repositories;

import org.springframework.data.repository.Repository;

import com.example.personalaccounting.entities.Category;

public interface CategoryRepository extends Repository<Category, Long> {
    public Category findByName(String name);
}
