package com.example.personalaccounting.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

import com.example.personalaccounting.dataobjects.OperationFilters;
import com.example.personalaccounting.dataobjects.OperationFilters.OperationType;
import com.example.personalaccounting.entities.Account;
import com.example.personalaccounting.entities.Category;
import com.example.personalaccounting.entities.Operation;
import com.example.personalaccounting.entities.Category.CategoryType;
import com.example.personalaccounting.repositories.AccountRepository;
import com.example.personalaccounting.repositories.CategoryRepository;
import com.example.personalaccounting.repositories.OperationRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/operation")
public class OperationController {
    private static final String OPERATION_TO_CREATE = "operationToCreate";
    private static final String OPERATION_TO_CHANGE = "operationToChange";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OperationRepository operationRepository;

    @GetMapping("/create")
    public String createOperation(Model model) {
        Operation operationToCreate = new Operation();
        operationToCreate.setDateMade(LocalDate.now());

        model.addAttribute(OPERATION_TO_CREATE, operationToCreate);
       
        return "operation-creation";
    }

    @PostMapping("/create")
    public String postOperation(
        @Valid @ModelAttribute(OPERATION_TO_CREATE) Operation operation, 
        BindingResult bindingResult) {
        validateOperation(operation, bindingResult);

        if (bindingResult.hasErrors()) {
            return "operation-creation";
        }

        Account sourceAccount = operation.getAccount();
        Category category = operation.getCategory();
        Operation paired = operation.getPairedOperation();
        BigDecimal balanceChange = operation.getBalanceChange();

        Account receivingAccount = null;
        if (paired != null) {
            receivingAccount = paired.getAccount();

            operation.setBalanceChange(balanceChange.negate());
            paired.setBalanceChange(balanceChange);
            paired.setPairedOperation(operation);
            paired.setDateMade(operation.getDateMade());
        } else if (category.getCategoryType() == CategoryType.Expense) {
            operation.setBalanceChange(operation.getBalanceChange().negate());
        }

        operationRepository.save(operation);

        sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance().add(operation.getBalanceChange()));
        accountRepository.save(sourceAccount);

        if (receivingAccount != null) {
            receivingAccount.setCurrentBalance(receivingAccount.getCurrentBalance().add(balanceChange));
            accountRepository.save(receivingAccount);
        }

        return "redirect:/operation";
    }

    @GetMapping
    public String getOperations(Model Model) {
        Model.addAttribute("operations", operationRepository.findAllByOrderByIdDescDateMadeDesc());
        Model.addAttribute("operationFilters", new OperationFilters());
        return "operation-listing";
    }

    @GetMapping("/edit/{id}")
    public String editOperation(@PathVariable("id") Long id, Model model) {
        model.addAttribute(OPERATION_TO_CHANGE, operationRepository.findById(id).orElseThrow());
        return "operation-change";
    }

    @PostMapping("/edit")
    public String postOperationEdit(@Valid @ModelAttribute(OPERATION_TO_CHANGE) Operation operation, BindingResult bindingResult) {
        bindingResult = validateOperation(operation, bindingResult);
        if (bindingResult.hasErrors()) {
            return "operation-change";
        }

        cancelEffectsOfOriginalOperation(operation);

        Category category = operation.getCategory();
        Operation paired = operation.getPairedOperation();
        BigDecimal balanceChange = operation.getBalanceChange();

        if (paired != null) {
            operation.setBalanceChange(balanceChange.negate());
            paired.setBalanceChange(balanceChange);
            paired.setPairedOperation(operation);
            paired.setDateMade(operation.getDateMade());
        } else if (category.getCategoryType() == CategoryType.Expense) {
            operation.setBalanceChange(balanceChange.negate());
        }

        operationRepository.save(operation);

        Account sourceAccount = operation.getAccount();
        sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance().add(operation.getBalanceChange()));
        accountRepository.save(sourceAccount);

        if (paired != null) {
            Account receivingAccount = paired.getAccount();

            receivingAccount.setCurrentBalance(receivingAccount.getCurrentBalance().add(balanceChange));
            accountRepository.save(receivingAccount);
        }

        return "redirect:/operation";
    }

    @GetMapping("/delete/{id}")
    public String deleteOperation(@PathVariable("id") Long operationId, Model model) {
        model.addAttribute("operationToDelete", operationRepository.findById(operationId).orElseThrow());
        return "operation-delete-confirmation";
    }

    @PostMapping("/delete")
    public String postOperationDelete(Operation operation) {
        Account source = operation.getAccount();
        source.setCurrentBalance(source.getCurrentBalance().subtract(operation.getBalanceChange()));
        accountRepository.save(source);
        
        Operation pair = operation.getPairedOperation();
        Account receiver = null;
        if (pair != null) {
            receiver = pair.getAccount();
            receiver.setCurrentBalance(receiver.getCurrentBalance().subtract(pair.getBalanceChange()));
            accountRepository.save(receiver);

            operation.setPairedOperation(null);
            operation = operationRepository.save(operation);
            operationRepository.delete(pair);
        }

        operationRepository.delete(operation);

        return "redirect:/operation";
    }

    private BindingResult validateOperation(Operation operation, BindingResult bindingResult) {
        Category category = operation.getCategory();
        Operation paired = operation.getPairedOperation();

        if (category == null && paired == null) {
            bindingResult.addError(new FieldError(OPERATION_TO_CHANGE, "category",
                null, true, null, null, "Can't be empty simultaneously with receiver account"));
        } else if (category != null && paired != null) {
            bindingResult.addError(new FieldError(OPERATION_TO_CHANGE, "category",
                null, true, null, null, "Receiver operation can't have category"));
        } 
        
        Account sourceAccount = operation.getAccount();
        Account receivingAccount = paired == null ? null : paired.getAccount();
        if (sourceAccount != null && sourceAccount.equals(receivingAccount)) {
            String idForPairedSelector = paired.getId() != null ? paired.getId().toString() : "A" + receivingAccount.getId();

            bindingResult.addError(new FieldError(OPERATION_TO_CHANGE, "pairedOperation",
                idForPairedSelector, true, null, null, "Receiver account can't be the same as source"));
        }

        BigDecimal balanceChange = operation.getBalanceChange();
        if (balanceChange != null && balanceChange.signum() != 1) {
            bindingResult.addError(new FieldError(OPERATION_TO_CHANGE, "balanceChange",
                balanceChange, true, null, null, "Balance change must be positive"));
        }

        return bindingResult;
    }

    private void cancelEffectsOfOriginalOperation(Operation editedOperation) {
        Operation originalOperation = operationRepository.findById(editedOperation.getId()).orElseThrow();

        Account originalSource = originalOperation.getAccount();
        originalSource.setCurrentBalance(originalSource.getCurrentBalance().subtract(originalOperation.getBalanceChange()));
        accountRepository.save(originalSource);
        
        Operation originalPair = originalOperation.getPairedOperation();
        Account originalReceiver = null;
        if (originalPair != null) {
            originalReceiver = originalPair.getAccount();
            originalReceiver.setCurrentBalance(originalReceiver.getCurrentBalance().subtract(originalPair.getBalanceChange()));
            accountRepository.save(originalReceiver);
        }

        Account editedSource = editedOperation.getAccount();
        if (editedSource.equals(originalSource)) {
            editedSource.setCurrentBalance(originalSource.getCurrentBalance());
        } else if (editedSource.equals(originalReceiver)) {
            editedSource.setCurrentBalance(originalReceiver.getCurrentBalance());
        }

        Operation editedPair = editedOperation.getPairedOperation();
        if (editedPair != null) {
            Account editedReceiver = editedPair.getAccount();
            
            if (editedReceiver.equals(originalSource)) {
                editedReceiver.setCurrentBalance(originalSource.getCurrentBalance());
            } else if (editedReceiver.equals(originalReceiver)) {
                editedReceiver.setCurrentBalance(originalReceiver.getCurrentBalance());
            }

            if (originalPair != null) {
                editedPair.setId(originalPair.getId());
            }
        } else if (originalPair != null) {
            operationRepository.delete(originalPair);
        }
    }

    @ModelAttribute("allAccounts") 
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @ModelAttribute("allCategories") 
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @ModelAttribute("allExpenseCategories") 
    public List<Category> getAllExpenseCategories() {
        return categoryRepository.findByCategoryTypeOrderById(CategoryType.Expense);
    }

    @ModelAttribute("allIncomeCategories") 
    public List<Category> getAllIncomeCategories() {
        return categoryRepository.findByCategoryTypeOrderById(CategoryType.Income);
    }

    @ModelAttribute("allOperationTypes")
    public List<OperationType> getAllOperationTypes() {
        return Arrays.asList(OperationType.values());
    }
}
