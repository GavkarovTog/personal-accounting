package com.example.personal_accounting.user_specific;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "user_number",
    uniqueConstraints = @UniqueConstraint(columnNames = {"username"})
)
public class UserNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_number_seq")
    @Column(name = "user_number")
    private Long userNumber;

    private String username;

    public Long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Long userNumber) {
        this.userNumber = userNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof UserNumber) {
            UserNumber oth = (UserNumber) obj;

            return userNumber.equals(oth.userNumber);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNumber, username);
    }
}