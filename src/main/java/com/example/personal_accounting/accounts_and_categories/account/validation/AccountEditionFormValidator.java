package com.example.personal_accounting.accounts_and_categories.account.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.personal_accounting.accounts_and_categories.account.Account;
import com.example.personal_accounting.accounts_and_categories.account.AccountService;
import com.example.personal_accounting.accounts_and_categories.account.dto.AccountEditionForm;

public class AccountEditionFormValidator implements Validator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountEditionForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null || ! supports(target.getClass())) {
            return;
        }

        AccountEditionForm editionForm = (AccountEditionForm) target;
        Account account = accountService.getAccount(editionForm.id).orElseThrow();

        if (! account.getName().equals(editionForm.name) && accountService.suchAccountExists(editionForm.name)) {
            errors.rejectValue("name", "acc.edt.used.name");
        }
    }
}

