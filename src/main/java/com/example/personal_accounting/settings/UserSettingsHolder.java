package com.example.personal_accounting.settings;

public class UserSettingsHolder {
    private UserSettings userSettings = null;
    private boolean isInitialized = false;

    public boolean hasSetup() {
        return userSettings != null;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setUserSettings(UserSettings userSettings) {
        isInitialized = true;
        this.userSettings = userSettings;
    }
}
