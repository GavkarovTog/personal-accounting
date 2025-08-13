package com.example.personal_accounting.shared_entities;

import java.math.BigDecimal;

import com.example.personal_accounting.user_specific.UserNumber;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "operation_member")
abstract public class OperationMember {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_member_seq")
    @Column(name = "operation_member_id")
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_number")
    private UserNumber userNumber;

    abstract public void addToBalance(BigDecimal additive);

    abstract public void subtractFromBalance(BigDecimal subtractive);

    abstract public BigDecimal getBalance();

    abstract public boolean isAccount();

    abstract public boolean isCategory();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserNumber getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(UserNumber userNumber) {
        this.userNumber = userNumber;
    }
}