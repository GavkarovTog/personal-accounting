package com.example.personal_accounting.accounts_and_categories.category;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.personal_accounting.accounts_and_categories.category.dto.CategoryCreationForm;
import com.example.personal_accounting.accounts_and_categories.category.dto.CategoryEditionForm;
import com.example.personal_accounting.accounts_and_categories.category.validation.CategoryCreationFormValidator;
import com.example.personal_accounting.accounts_and_categories.category.validation.CategoryEditionFormValidator;

@Controller
@RequestMapping(path = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryCreationFormValidator categoryCreationFormValidator;

    @Autowired
    private CategoryEditionFormValidator categoryEditionFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(categoryCreationFormValidator);
        binder.addValidators(categoryEditionFormValidator);
    }

    @GetMapping("/create")
    public String createCategory(CategoryCreationForm categoryCreationForm, @RequestParam(name = "category-type") String typeParam) {
        CategoryType type = CategoryType.Expense;
        if (typeParam.equalsIgnoreCase("income")) {
            type = CategoryType.Income;
        }

        categoryCreationForm.categoryType = type;

        return "create-category";
    }

    @PostMapping("/create")
    public String createCategory(@Validated CategoryCreationForm categoryCreationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-category";
        }

        categoryService.createCategory(categoryCreationForm.name, categoryCreationForm.categoryType);

        return "redirect:/accounts-and-categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(CategoryEditionForm categoryEditionForm, @PathVariable long id) {
        Optional<Category> category = categoryService.getById(id);
        if (! category.isPresent()) {
            throw new UnsupportedOperationException("Unknown category");
        }

        Category categoryEntity = category.orElseThrow();
        categoryEditionForm.name = categoryEntity.getName();
        categoryEditionForm.id = id;

        return "edit-category";
    }

    @PostMapping("/edit")
    public String editCategory(@Validated CategoryEditionForm categoryEditionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-category";
        }

        categoryService.renameCategory(categoryEditionForm.id, categoryEditionForm.name);

        return "redirect:/accounts-and-categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(Model model, @PathVariable long id) {
        Category category = categoryService.getById(id).orElseThrow();
        model.addAttribute("categoryName", category.getName());

        return "delete-category";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return "redirect:/accounts-and-categories";
    }
}