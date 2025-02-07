package com.example.personalaccounting.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Account account;

    @ManyToOne
    Category category;

    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    @OneToOne
    Operation pairedOperation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    @NotNull
    LocalDate dateMade;

    @NotNull
    BigDecimal balanceChange;

    public static Operation emptyWithAccount(Account account) {
        Operation operation = new Operation();
        operation.account = account;
        return operation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPairedOperation(Operation operation) {
        this.pairedOperation = operation;
    }

    public void setDateMade(LocalDate dateMade) {
        this.dateMade = dateMade;
    }

    public void setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Category getCategory() {
        return category;
    }

    public Operation getPairedOperation() {
        return pairedOperation;
    }

    public LocalDate getDateMade() {
        return dateMade;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }
}
