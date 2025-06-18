package com.example.personal_accounting.settings;

import java.time.ZoneId;
import java.util.Currency;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.personal_accounting.settings.dto.DateFormat;
import com.example.personal_accounting.settings.dto.UserSettingsForm;
import com.example.personal_accounting.settings.dto.ZoneIdToDisplay;

@Controller
public class UserSettingsController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private UserSettingsHolder userSettingsHolder;

    @GetMapping("/settings")
    public String settings(UserSettingsForm form, ZoneId userZoneId) {
        if (userZoneId != null) {
            form.setTimeZone(userZoneId.getId());
        }

        logger.debug("userZoneId {}", userZoneId);
        
        return "settings";
    }

    @PostMapping("/settings")
    public String settings(@Validated UserSettingsForm form, BindingResult bindingResult) {
        logger.debug("currency {}", form.currency);
        logger.debug("timeZone {}", form.timeZone);
        logger.debug("dateFormat {}", form.dateFormat);

        if (bindingResult.hasErrors()) {
            return "settings";
        }

        UserSettings userSettings = userSettingsService.saveSetupForCurrentUser(form.currency, form.timeZone, form.dateFormat);
        userSettingsHolder.setUserSettings(userSettings);

        return "redirect:/";
    }

    @ModelAttribute("currencies")
    public List<Currency> currencies() {
       return userSettingsService.getCurrencies();
    }

    @ModelAttribute("zones")
    public List<ZoneIdToDisplay> zones() {
        return userSettingsService.getZones();
    }

    @ModelAttribute("dateFormats")
    public List<DateFormat> dateFormats() {
        return userSettingsService.getDateFormats();
    }
}
