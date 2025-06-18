package com.example.personal_accounting.settings.dto;

import jakarta.validation.constraints.NotBlank;

public class UserSettingsForm {
    @NotBlank
    public String currency;

    @NotBlank
    public String timeZone;

    @NotBlank
    public String dateFormat;
   
    public String getCurrency() {
        return currency;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
