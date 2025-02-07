package com.example.personalaccounting.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.example.personalaccounting.entities.Category;
import com.example.personalaccounting.repositories.CategoryRepository;

public class CategoryFormatter implements Formatter<Category> {
    private CategoryRepository categoryRepository;

    @Override
    public String print(Category category, Locale locale) {
        return String.valueOf(category.getId());
    }

    @Override
    public Category parse(String categoryId, Locale locale) throws ParseException {
        return categoryRepository.findById(Long.parseLong(categoryId));
    }
}
