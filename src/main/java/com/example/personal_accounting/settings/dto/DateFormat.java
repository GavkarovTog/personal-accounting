package com.example.personal_accounting.settings.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateFormat {
    public String pattern;
    public String displayFormat;

    public DateFormat(String pattern) {
        this.pattern = pattern;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        displayFormat = LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC).format(dtf);
    }
}
