package com.onurtokat.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class WeeklyDateCalculator {

    public static long getCurrentDate() {
        return Long.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    public static long getPreviousWeekDate() {
        return Long.valueOf(LocalDate.now()
                .minus(1, ChronoUnit.WEEKS).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
}
