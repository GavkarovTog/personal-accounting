package com.example.personal_accounting.operation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.personal_accounting.accounts_and_categories.account.Account;
import com.example.personal_accounting.accounts_and_categories.account.AccountService;
import com.example.personal_accounting.accounts_and_categories.category.Category;
import com.example.personal_accounting.accounts_and_categories.category.CategoryService;
import com.example.personal_accounting.operation.dto.OperationCreationForm;
import com.example.personal_accounting.operation.dto.OperationEditionForm;
import com.example.personal_accounting.operation.dto.OperationType;
import com.example.personal_accounting.operation.validation.OperationValidator;
import com.example.personal_accounting.settings.UserSettingsService;

@RequestMapping(path = "/operation")
@Controller
public class OperationController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OperationService operationService;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OperationValidator operationValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(operationValidator);
    }

    @GetMapping
    public String getOperations(Model model) {
        model.addAttribute("username", userSettingsService.getCurrentUsername());
        return "operations";
    }

    @GetMapping("/create")
    public String createOperation(OperationCreationForm operationCreationForm, ZoneId zoneId) {
        operationCreationForm.setDate(LocalDate.now(zoneId));

        return "create-operation";
    }

    @PostMapping("/create")
    public String createOperation(@Validated OperationCreationForm operationCreationForm, BindingResult bindingResult) {
        logger.debug("OperationType -> {}", operationCreationForm.operationType);
        logger.debug("From -> {}", operationCreationForm.from);
        logger.debug("To -> {}", operationCreationForm.to);
        logger.debug("Date -> {}", operationCreationForm.date);
        if (bindingResult.hasErrors()) {
            return "create-operation";
        }

        operationService.createOperation(operationCreationForm.from, operationCreationForm.to,
            operationCreationForm.amount, operationCreationForm.date);

        return "redirect:/operation";
    }

    @GetMapping("/edit/{id}")
    public String editOperation(OperationEditionForm operationEditionForm, @PathVariable long id) {
        Optional<Operation> operation = operationService.getOperation(id);
        if (! operation.isPresent()) {
            throw new UnsupportedOperationException("Operation with such id is absent");
        }

        Operation operationEntity = operation.orElseThrow();
        if (operationEntity.getDestination().isCategory()) {
            operationEditionForm.operationType = OperationType.Expense;
        } else if (operationEntity.getSource().isCategory()) {
            operationEditionForm.operationType = OperationType.Income;
        } else {
            operationEditionForm.operationType = OperationType.Transfer;
        }

        operationEditionForm.from = operationEntity.getSource().getId();
        operationEditionForm.to = operationEntity.getDestination().getId();
        operationEditionForm.amount = operationEntity.getBalanceChange();
        operationEditionForm.date = operationEntity.getDate();

        return "edit-operation";
    }

    @PostMapping("/edit/{id}")
    public String editOperation(@Validated OperationEditionForm operationEditionForm, BindingResult bindingResult, @PathVariable long id) {
        Optional<Operation> operation = operationService.getOperation(id);
        if (! operation.isPresent()) {
            throw new UnsupportedOperationException("Operation with such id is absent");
        }
        
        if (bindingResult.hasErrors()) {
            return "edit-operation";
        }

        operationService.editOperation(
            id,
            operationEditionForm.from,
            operationEditionForm.to,
            operationEditionForm.amount,
            operationEditionForm.date
        );

        return "redirect:/operation";
    }

    @GetMapping("/delete/{id}")
    public String deleteOperation(@PathVariable long id, Model model) {
        if (! operationService.getOperation(id).isPresent()) {
            throw new UnsupportedOperationException("Unable to delete missing operation");
        }

        return "delete-operation";
    }

    @PostMapping("/delete/{id}")
    public String deleteOperation(@PathVariable long id) {
        if (! operationService.getOperation(id).isPresent()) {
            throw new UnsupportedOperationException("Unable to delete missing operation");
        }

        operationService.deleteOperation(id);

        return "redirect:/operation";
    }
    
    @ModelAttribute("currency")
    public String currency() {
        return userSettingsService.getCurrentUserSetup().getCurrency();
    }

    @ModelAttribute("accounts")
    public List<Account> accounts() {
        return accountService.getAllAccounts();
    }

    @ModelAttribute("expenses")
    public List<Category> expenses() {
        return categoryService.getAllExpenses();
    }

    @ModelAttribute("incomes")
    public List<Category> incomes() {
        return categoryService.getAllIncomes();
    }

    @ModelAttribute("today")
    public String today() {
        return LocalDate.now().toString();
    }

    @ModelAttribute("operations")
    public List<Operation> operations() {
        return operationService.getAllOperations();
    }
}
