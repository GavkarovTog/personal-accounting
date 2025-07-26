package com.example.personal_accounting.operation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class OperationCreationForm {
    @NotNull
    public OperationType operationType;

    @NotNull(message = "{empt.fld.err}")
    public Long from;

    @NotNull(message = "{empt.fld.err}")
    public Long to;

    @Positive
    @NotNull(message = "{empt.fld.err}")
    public BigDecimal amount;

    @DateTimeFormat(iso = ISO.DATE)
    @PastOrPresent
    @NotNull(message = "{empt.fld.err}")
    public LocalDate date;

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
