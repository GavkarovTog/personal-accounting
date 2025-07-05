package com.example.personal_accounting.accounts_and_categories.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AccountCreationForm {
    @Size(min = 4, max = 40)
    @NotBlank
    public String name;
    
    @NotBlank
    @Pattern(regexp = "^-?(0|[1-9][0-9]*)(\\.[0-9]+)?$", message = "Must be numeric")
    public String startBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(String startBalance) {
        this.startBalance = startBalance;
    }
}
