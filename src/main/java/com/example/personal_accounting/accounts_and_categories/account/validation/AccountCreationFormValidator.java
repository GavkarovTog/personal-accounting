package com.example.personal_accounting.accounts_and_categories.account.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.personal_accounting.accounts_and_categories.account.AccountService;
import com.example.personal_accounting.accounts_and_categories.account.dto.AccountCreationForm;

public class AccountCreationFormValidator implements Validator {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountCreationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.debug("In AccountCreationFormValidator");

        if (target == null || ! supports(target.getClass())) {
            return;
        }

        AccountCreationForm creationForm = (AccountCreationForm) target;
        if (accountService.suchAccountExists(creationForm.name)) {
            errors.rejectValue("name", "acc.crt.used.name");
        }
    }
}
