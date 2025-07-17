package com.example.personal_accounting.accounts_and_categories.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.personal_accounting.user_specific.UserNumberService;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserNumberService userNumberService;

    public void createCategory(String name, CategoryType categoryType) {
        Category category = new Category();
        category.setName(name);
        category.setCategoryType(categoryType);
        category.setUserNumber(userNumberService.getCurrentUserNumber());

        categoryRepository.save(category);
    }

    public List<Category> getAllIncomes() {
        return categoryRepository.findAllByCategoryTypeAndUserNumber(CategoryType.Income, userNumberService.getCurrentUserNumber());
    }

    public List<Category> getAllExpenses() {
        return categoryRepository.findAllByCategoryTypeAndUserNumber(CategoryType.Expense, userNumberService.getCurrentUserNumber());
    }

    public boolean suchCategoryExists(String name) {
        return categoryRepository.existsByNameAndUserNumber(name, userNumberService.getCurrentUserNumber());
    }

    public Optional<Category> getById(long id) {
        return categoryRepository.findByIdAndUserNumber(id, userNumberService.getCurrentUserNumber());
    }

    public void renameCategory(long id, String newName) {
        Category category = categoryRepository.findByIdAndUserNumber(id, userNumberService.getCurrentUserNumber()).orElseThrow();
        category.setName(newName);

        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(long id) {
        categoryRepository.deleteByIdAndUserNumber(id, userNumberService.getCurrentUserNumber());
    }
}
