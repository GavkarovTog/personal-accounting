package com.example.personalaccounting.controllers;

import java.beans.Transient;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.personalaccounting.entities.Account;
import com.example.personalaccounting.entities.Account_;
import com.example.personalaccounting.entities.Category;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;


@Controller
@RequestMapping("/category")
public class CategoryController {
    private static final String CATEGORY_TO_CREATE = "categoryToCreate";

    @PersistenceContext
    private Session session;

    @GetMapping("/create")
    public String createCategory(Model model) {
        model.addAttribute(CATEGORY_TO_CREATE, new Category());
        return "category-creation";
    }

    @PostMapping("/create")
    @Transactional
    public String postCategory(@ModelAttribute(CATEGORY_TO_CREATE) Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category-creation";
        }

        if (existsCategoryWithName(category.getName())) {
            bindingResult.addError(new FieldError(CATEGORY_TO_CREATE, "name",
                category.getName(), true, null, null, "Must be unique"));
            return "category-creation";
        }

        session.persist(category);

        return "redirect:/category";
    }

    private boolean existsCategoryWithName(String name) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.where(cb.equal(root.get(Account_.name), name));
        Account withTheSameName = session.createQuery(query).getSingleResultOrNull();

        return withTheSameName != null;
    }
}
