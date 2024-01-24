package com.yanolja_final.domain.packages.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public record PackageDateTimeDTO(
    String date,
    String dayOfWeek,
    String time
) {

    public static PackageDateTimeDTO from(LocalDate departureDate, LocalTime departureTime) {
        return new PackageDateTimeDTO(
            departureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            departureDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN),
            departureTime == null ? "" : departureTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
