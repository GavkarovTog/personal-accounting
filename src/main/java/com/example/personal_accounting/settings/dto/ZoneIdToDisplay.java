package com.example.personal_accounting.settings.dto;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

public class ZoneIdToDisplay {
    public final ZoneId zoneId;
    public final String displayRepr;

    public ZoneIdToDisplay(ZoneId zoneId) {
        this.zoneId = zoneId;
        Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        displayRepr = String.format("(%s) %s (%s)",
            zoneId.getDisplayName(TextStyle.SHORT, locale),
            zoneId.getDisplayName(TextStyle.FULL, locale),
            zoneId.getDisplayName(TextStyle.NARROW, locale)
        );
    }
    
    public ZoneId getZoneId() {
        return zoneId;
    }

    public String getDisplayRepr() {
        return displayRepr;
    }
}
