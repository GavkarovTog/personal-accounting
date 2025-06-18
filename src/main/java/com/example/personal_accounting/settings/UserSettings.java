package com.example.personal_accounting.settings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_settings")
public class UserSettings {
    @Id
    private String username;
    private String currency;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "date_format")
    private String dateFormat;

    public String getUsername() {
        return username;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setUsername(String username) {
        this.username = username;
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