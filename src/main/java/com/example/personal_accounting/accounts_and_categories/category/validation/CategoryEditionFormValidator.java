package com.example.personal_accounting.accounts_and_categories.category.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.personal_accounting.accounts_and_categories.category.Category;
import com.example.personal_accounting.accounts_and_categories.category.CategoryService;
import com.example.personal_accounting.accounts_and_categories.category.dto.CategoryEditionForm;

public class CategoryEditionFormValidator implements Validator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryEditionForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("In CategoryEditionFormValidator");

        if (target == null || ! supports(target.getClass())) {
            return;
        }
        CategoryEditionForm editionForm = (CategoryEditionForm) target;
        Category category = categoryService.getById(editionForm.id).orElseThrow();
        if (editionForm.name != null && ! category.getName().equals(editionForm.name) && categoryService.suchCategoryExists(editionForm.name)) {
            errors.rejectValue("name", "ctg.edt.used.name");
        }
    }
}
