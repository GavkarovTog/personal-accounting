package com.example.personal_accounting.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationForm {
    @Size(min = 5, max = 40)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Must be alphanumeric")
    public String username;

    @Size(min = 8, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Must be alphanumeric")
    public String password;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Must be alphanumeric")
    public String passwordRepeat;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }
}
