package com.example.personal_accounting.settings;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserDataInitializingAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private UserSettingsHolder userSettingsHolder;

    @Autowired
    private UserSettingsService userSettingsService;

    public UserDataInitializingAuthenticationSuccessHandler() {
        super("/");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        logger.debug("before default SimpleUrlAuthHandler");
        super.onAuthenticationSuccess(request, response, authentication);
        logger.debug("after default SimpleUrlAuthHandler");

        String username = userSettingsService.getUsernameFromAuthentication(authentication);
        if (username != null) {
            UserSettings userSettings = userSettingsService.getSetup(username);
            userSettingsHolder.setUserSettings(userSettings);
        }
        logger.debug("SimpleUrlAuthHandler username -> {}", username);
    }
}