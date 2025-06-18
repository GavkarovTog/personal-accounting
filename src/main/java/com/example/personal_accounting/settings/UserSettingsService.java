package com.example.personal_accounting.settings;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.personal_accounting.settings.dto.DateFormat;
import com.example.personal_accounting.settings.dto.ZoneIdToDisplay;

@Service
public class UserSettingsService {
    private List<Currency> availableCurrencies;
    private List<ZoneIdToDisplay> zoneIds;
    private List<DateFormat> dateFormats;

    @Autowired
    private UserSettingsRepository userSettingsRepository;
    
    {
        availableCurrencies = new ArrayList<>(Currency.getAvailableCurrencies());
        availableCurrencies.remove(Currency.getInstance("XXX"));
        availableCurrencies.sort((ccy1, ccy2) -> ccy1.getCurrencyCode().compareTo(ccy2.getCurrencyCode()));

        zoneIds = ZoneId.getAvailableZoneIds()
            .stream()
            .map(id -> new ZoneIdToDisplay(ZoneId.of(id)))
            .sorted((zone1, zone2) -> zone1.displayRepr.compareTo(zone2.displayRepr))
            .toList();

        dateFormats = List.of(
            new DateFormat("yyyy-MM-dd"),
            new DateFormat("yyyy.MM.dd"),
            new DateFormat("dd/MM/yyyy"),
            new DateFormat("dd/MM/yy"),
            new DateFormat("dd.MM.yyyy"),
            new DateFormat("dd.MM.yy"),
            new DateFormat("MM/dd/yyyy"),
            new DateFormat("MM/dd/yy"),
            new DateFormat("d LLLL, yyyy"),
            new DateFormat("yyyy LLLL d")
        );
    }

    public List<Currency> getCurrencies() {
        return availableCurrencies;
    }

    public List<ZoneIdToDisplay> getZones() {
        return zoneIds;
    }
  
    public List<DateFormat> getDateFormats() {
        return dateFormats;
    }

    public boolean hasSetup(String username) {
        return false;
    }

    public boolean hasCurrentUserSetup() {
        String username = getCurrentUsername();
        if (username == null) {
            return false;
        }
        return hasSetup(username);
    }

    public UserSettings saveSetup(String username, String currency, String timeZone, String dateFormat) {
        UserSettings userSettings = new UserSettings();
        userSettings.setUsername(username);
        userSettings.setCurrency(currency);
        userSettings.setTimeZone(timeZone);
        userSettings.setDateFormat(dateFormat);

        userSettingsRepository.save(userSettings);
        return userSettings;
    }

    public UserSettings saveSetupForCurrentUser(String currency, String timeZone, String dateFormat) {
        String username = getCurrentUsername();
        if (username == null) {
            return null;
        }

        return saveSetup(username, currency, timeZone, dateFormat);
    }

    public UserSettings getSetup(String username) {
        Optional<UserSettings> userSettings = userSettingsRepository.findById(username);
        return userSettings.orElse(null);
    }

    public UserSettings getCurrentUserSetup() {
        String username = getCurrentUsername();
        if (username == null) {
            return null;
        }

        return getSetup(username);
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        
        return getUsernameFromAuthentication(authentication);
    }

    public String getUsernameFromAuthentication(Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return ((User) authentication.getPrincipal()).getUsername();
    }
}
