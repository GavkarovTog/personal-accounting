package com.example.personal_accounting.security.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.personal_accounting.security.SecurityService;
import com.example.personal_accounting.security.dto.RegistrationForm;

public class RegistrationFormValidator implements Validator {
    @Autowired
    private SecurityService securityService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        if (target == null || ! supports(target.getClass())) {
            throw new UnsupportedOperationException("Trying to validate null or not supported class object with RegistrationFormValidator");
        }

        RegistrationForm registrationForm = (RegistrationForm) target;
        if (securityService.suchUsernameExists(registrationForm.getUsername())) {
            errors.rejectValue("username", "reg.used.name");
        }

        String password = registrationForm.getPassword();
        String passwordRepeat = registrationForm.getPasswordRepeat();

        if (! password.equals(passwordRepeat)) {
            errors.rejectValue("passwordRepeat", "pass.not.eq.repeat");
        }
    }
}
