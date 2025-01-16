package com.example.personalaccounting.controllers;

import java.util.List;

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

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RequestMapping("/account")
@Controller
public class AccountController {
    private static final String ACCOUNT_TO_CREATE = "accountToCreate";

    @PersistenceContext
    private Session session;

    @GetMapping("/create")
    public String createAccount(Model model) {
        model.addAttribute(ACCOUNT_TO_CREATE, new Account());

        return "account-creation";
    }

    @PostMapping
    @Transactional
    public String postAccount(@Valid @ModelAttribute(ACCOUNT_TO_CREATE) Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account-creation";
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.where(cb.equal(root.get(Account_.name), account.getName()));
        Account withTheSameName = session.createQuery(query).getSingleResultOrNull();
        if (withTheSameName == null) {
            session.persist(account);
        } else {
            bindingResult.addError(new FieldError(ACCOUNT_TO_CREATE, "name", "This name is used already"));
            return "account-creation";
        }

        return "redirect:account";
    }

    @GetMapping
    @Transactional
    public String listAccounts(Model model) {
        CriteriaQuery<Account> query = session.getCriteriaBuilder()
            .createQuery(Account.class);
        query.from(Account.class);
        List<Account> accounts = session.createQuery(query)
            .getResultList();

        model.addAttribute("accounts", accounts);

        return "account-list";
    }
}
