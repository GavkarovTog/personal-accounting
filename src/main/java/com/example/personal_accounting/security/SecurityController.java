package com.example.personal_accounting.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.personal_accounting.security.dto.LoginForm;
import com.example.personal_accounting.security.dto.RegistrationForm;
import com.example.personal_accounting.security.validation.RegistrationFormValidator;

import jakarta.validation.Valid;

@Controller
public class SecurityController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RegistrationFormValidator registrationFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(registrationFormValidator);
    }

    @GetMapping("/register")
    public String getRegister(RegistrationForm registrationForm) {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        logger.trace("username = {}", registrationForm.username);
        logger.trace("password = {}", registrationForm.password);
        logger.trace("passwordRepeat = {}", registrationForm.passwordRepeat);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        securityService.register(registrationForm.username, registrationForm.password);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(LoginForm loginForm) {
        return "login";
    }
}