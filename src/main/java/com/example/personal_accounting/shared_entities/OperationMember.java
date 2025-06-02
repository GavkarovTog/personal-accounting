package com.example.personal_accounting.shared_entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "operation_member")
abstract public class OperationMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    abstract public void addToBalance(BigDecimal additive);

    abstract public void subtractFromBalance(BigDecimal subtractive);
}