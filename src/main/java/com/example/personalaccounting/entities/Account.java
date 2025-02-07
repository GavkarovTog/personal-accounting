package com.example.personalaccounting.entities;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(min = 4, max=30)
    private String name;

    @NotNull
    private BigDecimal currentBalance;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Account) {
            Account other = (Account) obj;
            return this.id.equals(other.id);
        }

        return false;
    }
}
