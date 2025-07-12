package com.example.personal_accounting.accounts_and_categories.account;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.personal_accounting.accounts_and_categories.account.dto.AccountCreationForm;
import com.example.personal_accounting.accounts_and_categories.account.dto.AccountEditionForm;
import com.example.personal_accounting.accounts_and_categories.account.validation.AccountCreationFormValidator;
import com.example.personal_accounting.accounts_and_categories.account.validation.AccountEditionFormValidator;

@RequestMapping(path = "/account")
@Controller
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountCreationFormValidator accountCreationFormValidator;

    @Autowired
    private AccountEditionFormValidator accountEditionFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(accountCreationFormValidator);
        binder.addValidators(accountEditionFormValidator);
    }

    @GetMapping("/create")
    public String createAccount(AccountCreationForm accountCreationForm) {
        accountCreationForm.setStartBalance("0");
        return "create-account";
    }

    @PostMapping("/create")
    public String createAccount(@Validated AccountCreationForm accountCreationForm, BindingResult bindingResult) {
        logger.debug("name -> {}", accountCreationForm.name);
        logger.debug("startBalance -> {}", accountCreationForm.startBalance);

        if (bindingResult.hasErrors()) {
            return "create-account";
        }
        
        accountService.createAccount(accountCreationForm.name, new BigDecimal(accountCreationForm.startBalance));

        return "redirect:/accounts-and-categories";
    }

    @GetMapping("/edit/{accountId}")
    public String editAccount(AccountEditionForm accountEditionForm, @PathVariable long accountId) {
        Optional<Account> account = accountService.getAccount(accountId);
        if (! account.isPresent()) {
            throw new UnsupportedOperationException("Unknown account");
        }

        Account accountEntity = account.orElseThrow();

        accountEditionForm.setId(accountEntity.getId());
        accountEditionForm.setName(accountEntity.getName());

        return "edit-account";
    }

    @PostMapping("/edit/{accountId}")
    public String editAccount(@Validated AccountEditionForm accountEditionForm, BindingResult bindingResult, @PathVariable long accountId) {
        if (bindingResult.hasErrors()) {
            return "edit-account";
        }

        accountService.renameAccount(accountId, accountEditionForm.name);

        return "redirect:/accounts-and-categories";
    }

    @GetMapping("/delete/{accountId}")
    public String deleteAccount(Model model, @PathVariable long accountId) {
        Account account = accountService.getAccount(accountId).orElseThrow();
        
        model.addAttribute("accountName", account.getName());
        return "delete-account";
    }

    @PostMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable long accountId) {
        accountService.deleteAccount(accountId);
        return "redirect:/accounts-and-categories";
    }
}
