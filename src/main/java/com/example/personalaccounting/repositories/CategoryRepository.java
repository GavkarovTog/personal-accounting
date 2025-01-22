package com.example.personalaccounting.repositories;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.personalaccounting.entities.Category;

public interface CategoryRepository extends Repository<Category, Long> {
    public List<Category> findAll();

    public List<Category> findByCategoryTypeOrderById(Category.CategoryType categoryType);

    public Category findById(Long id);

    public boolean existsByName(String name);

    public void save(Category category);

    public void deleteById(Long id);
}
