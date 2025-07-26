package com.example.personal_accounting.operation;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.personal_accounting.shared_entities.OperationMember;
import com.example.personal_accounting.user_specific.UserNumber;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_seq")
    @Column(name = "operation_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_number")
    private UserNumber userNumber;

    private LocalDate date;

    @Column(name = "balance_change")
    private BigDecimal balanceChange;

    @JoinColumn(name = "source")
    @ManyToOne(cascade = CascadeType.MERGE)
    private OperationMember source;

    @JoinColumn(name = "destination")
    @ManyToOne(cascade = CascadeType.MERGE)
    private OperationMember destination;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserNumber getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(UserNumber userNumber) {
        this.userNumber = userNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
    }

    public OperationMember getSource() {
        return source;
    }

    public void setSource(OperationMember source) {
        this.source = source;
    }

    public OperationMember getDestination() {
        return destination;
    }

    public void setDestination(OperationMember destination) {
        this.destination = destination;
    }
}
