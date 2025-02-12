package com.example.personalaccounting.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.personalaccounting.entities.Account;
import com.example.personalaccounting.entities.Account_;
import com.example.personalaccounting.entities.Category;
import com.example.personalaccounting.entities.Category_;
import com.example.personalaccounting.entities.Operation;
import com.example.personalaccounting.entities.Operation_;
import com.example.personalaccounting.entities.Category.CategoryType;
import com.example.personalaccounting.repositories.CategoryRepository;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/category")
public class CategoryController {
    private static final String CATEGORY_TO_CREATE = "categoryToCreate";
    private static final String CATEGORY_TO_CHANGE = "categoryToChange";

    @PersistenceContext
    private Session session;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/create")
    public String createCategory(Model model) {
        Category categoryToCreate = new Category();
        categoryToCreate.setCategoryType(CategoryType.Expense);

        model.addAttribute(CATEGORY_TO_CREATE, categoryToCreate);
        return "category-creation";
    }

    @PostMapping("/create")
    @Transactional
    public String postCategory(@Valid @ModelAttribute(CATEGORY_TO_CREATE) Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category-creation";
        }

        if (categoryRepository.existsByName(category.getName())) {
            bindingResult.addError(new FieldError(CATEGORY_TO_CREATE, "name",
                category.getName(), true, null, null, "Must be unique"));
            return "category-creation";
        }

        categoryRepository.save(category);
        return "redirect:/category";
    }

    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> expenseCategories = categoryRepository.findByCategoryTypeOrderById(CategoryType.Expense);
        List<Category> incomeCategories = categoryRepository.findByCategoryTypeOrderById(CategoryType.Income);

        model.addAttribute("expenseCategories", expenseCategories);
        model.addAttribute("incomeCategories", incomeCategories);

        return "category-listing";
    }

    @GetMapping("/edit/{id}")
    public String changeCategoryName(@PathVariable("id") long id, Model model) {
        Category category = categoryRepository.findById(id);
        model.addAttribute(CATEGORY_TO_CHANGE, category);
        return "category-name-change";
    }

    @PostMapping("/edit")
    @Transactional
    public String postCategoryNameChange(@Valid @ModelAttribute(CATEGORY_TO_CHANGE) Category categoryToChange, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category-name-change";
        }

        String oldName = categoryRepository.findById(categoryToChange.getId()).getName();
        String newName = categoryToChange.getName();

        if (oldName.equals(newName)) {
            return "redirect:/category";
        } else if (categoryRepository.existsByName(newName)) {
            bindingResult.addError(new FieldError(CATEGORY_TO_CHANGE, "name",
                newName, true, null, null, "Must be unique"));
            return "category-name-change";
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<Category> query = cb.createCriteriaUpdate(Category.class);
        Root<Category> root = query.from(Category.class); 
        query.where(cb.equal(root.get(Category_.id), categoryToChange.getId()));
        query.set(root.get(Category_.name), newName);
        session.createMutationQuery(query).executeUpdate();

        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") long id, Model model) {
        Category category = categoryRepository.findById(id);
        model.addAttribute("categoryToDelete", category);
        return "category-delete-confirmation";
    }

    @PostMapping("/delete")
    @Transactional
    public String postCategoryDelete(Category categoryToDelete) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> getAccountsTotalChanges = cb.createQuery(Tuple.class);
        Root<Operation> root = getAccountsTotalChanges.from(Operation.class);
        getAccountsTotalChanges.multiselect(root.get(Operation_.account), cb.sum(root.get(Operation_.balanceChange)));
        getAccountsTotalChanges.groupBy(root.get(Operation_.account));
        getAccountsTotalChanges.where(cb.equal(root.get(Operation_.category), categoryToDelete));

        List<Tuple> accountChanges = session.createQuery(getAccountsTotalChanges).getResultList();
        for (Tuple tuple: accountChanges) {
            Account account = (Account) tuple.get(0);
            BigDecimal totalAccountChange = (BigDecimal) tuple.get(1);

            CriteriaUpdate<Account> revertAccountChange = cb.createCriteriaUpdate(Account.class);
            Root<Account> accountRoot = revertAccountChange.from(Account.class);
            revertAccountChange.where(cb.equal(accountRoot.get(Account_.id), account.getId()));
            revertAccountChange.set(accountRoot.get(Account_.currentBalance), account.getCurrentBalance().subtract(totalAccountChange));
            session.createMutationQuery(revertAccountChange).executeUpdate();
        }

        CriteriaDelete<Operation> deleteOperationsWithCategory = cb.createCriteriaDelete(Operation.class);
        root = deleteOperationsWithCategory.from(Operation.class);
        deleteOperationsWithCategory.where(cb.equal(root.get(Operation_.category), categoryToDelete));
        session.createMutationQuery(deleteOperationsWithCategory).executeUpdate();

        categoryRepository.deleteById(categoryToDelete.getId());
        return "redirect:/category";
    }
}
