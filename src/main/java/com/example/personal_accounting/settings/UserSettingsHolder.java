package com.example.personal_accounting.settings;

import org.springframework.beans.factory.annotation.Autowired;

public class UserSettingsHolder {
    private UserSettings userSettings = null;

    public boolean hasSetup() {
        return userSettings != null;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(@Autowired(required = false) UserSettings userSettings) {
        this.userSettings = userSettings;
    }
}
