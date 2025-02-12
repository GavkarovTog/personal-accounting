package com.example.personalaccounting.controllers;

import java.util.List;

import org.hibernate.Session;
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
import com.example.personalaccounting.entities.Operation;
import com.example.personalaccounting.entities.Operation_;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RequestMapping("/account")
@Controller
public class AccountController {
    private static final String ACCOUNT_TO_CREATE = "accountToCreate";
    private static final String ACCOUNT_TO_CHANGE = "accountToChange";

    @PersistenceContext
    private Session session;

    @GetMapping("/create")
    public String createAccount(Model model) {
        model.addAttribute(ACCOUNT_TO_CREATE, new Account());

        return "account-creation";
    }

    @PostMapping("/create")
    @Transactional
    public String postAccount(@Valid @ModelAttribute(ACCOUNT_TO_CREATE) Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account-creation";
        }
        
        if (existsAccountWithName(account.getName())) {
            bindingResult.addError(new FieldError(ACCOUNT_TO_CREATE, "name", account.getName(), 
            true, null, null, "Must be unique"));
            return "account-creation";
        }

        session.persist(account);
        return "redirect:/account";
    }

    @GetMapping
    @Transactional
    public String listAccounts(Model model) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.orderBy(cb.asc(root.get(Account_.id)));
        List<Account> accounts = session.createQuery(query)
            .getResultList();

        model.addAttribute("accounts", accounts);

        return "account-list";
    }

    @GetMapping(path="/edit/{id}")
    public String changeAccountName(@PathVariable("id") long accountId, Model model) {
        Account account = session.byId(Account.class).load(accountId);
        model.addAttribute(ACCOUNT_TO_CHANGE, account);
        System.out.println("ACCOUNT HAS BALANCE: " + account.getCurrentBalance());
        return "account-name-change";
    }

    @PostMapping(path="/edit")
    @Transactional
    public String postAccountNameChange(@Valid @ModelAttribute(ACCOUNT_TO_CHANGE) Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account-name-change";
        }

        Account oldNamedAccount = session.byId(Account.class).load(account.getId());
        
        if (oldNamedAccount.getName().equals(account.getName())) {
            return "redirect:/account";
        } else if (existsAccountWithName(account.getName())) {
            bindingResult.addError(new FieldError(ACCOUNT_TO_CHANGE, "name", account.getName(), 
            true, null, null, "Must be unique"));
            return "account-name-change";
        }
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<Account> query = cb.createCriteriaUpdate(Account.class);
        Root<Account> root = query.from(Account.class);
        query.where(
            cb.equal(root.get(Account_.id), account.getId())
        );
        query.set(root.get(Account_.name), account.getName());
        session.createMutationQuery(query).executeUpdate();

        return "redirect:/account";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteAccount(@PathVariable("id") Long accountId, Model model) {
        Account toDelete = session.byId(Account.class).load(accountId);
        model.addAttribute("accountToDelete", toDelete);
        return "account-delete-confirmation";
    }

    @PostMapping("/delete")
    @Transactional
    public String postAccountDeletion(Account account) {
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaUpdate<Operation> makeOperationsUnpaired = cb.createCriteriaUpdate(Operation.class);
        Root<Operation> operation = makeOperationsUnpaired.from(Operation.class);
        makeOperationsUnpaired.where(
            cb.and(
                cb.isNotNull(operation.get(Operation_.pairedOperation)),
                cb.equal(operation.get(Operation_.pairedOperation).get(Operation_.account), account)
            )
        );

        makeOperationsUnpaired.set(operation.get(Operation_.pairedOperation), 
            cb.nullLiteral(Operation.class));

        session.createMutationQuery(makeOperationsUnpaired).executeUpdate();

        CriteriaDelete<Operation> deleteAccountOperations = cb.createCriteriaDelete(Operation.class);
        operation = deleteAccountOperations.from(Operation.class);
        deleteAccountOperations.where(cb.equal(operation.get(Operation_.account), account));
        session.createMutationQuery(deleteAccountOperations).executeUpdate();        

        CriteriaDelete<Account> query = cb.createCriteriaDelete(Account.class);
        Root<Account> root = query.from(Account.class);
        query.where(cb.equal(root.get(Account_.id), account.getId()));
        session.createMutationQuery(query).executeUpdate();

        return "redirect:/account";
    }

    private boolean existsAccountWithName(String name) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.where(cb.equal(root.get(Account_.name), name));
        Account withTheSameName = session.createQuery(query).getSingleResultOrNull();

        return withTheSameName != null;
    }
}
