package com.example.personal_accounting.accounts_and_categories.category.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.personal_accounting.accounts_and_categories.category.CategoryService;
import com.example.personal_accounting.accounts_and_categories.category.dto.CategoryCreationForm;

public class CategoryCreationFormValidator implements Validator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryCreationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("In CategoryCreationFormValidator");

        if (target == null || ! supports(target.getClass())) {
            return;
        }

        CategoryCreationForm creationForm = (CategoryCreationForm) target;
        if (categoryService.suchCategoryExists(creationForm.name)) {
            errors.rejectValue("name", "ctg.crt.used.name");
        }
    }
}
