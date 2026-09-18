package calendar;

import java.time.DayOfWeek;
import java.time.Month;

public record CalendarDay(
        int day,
        Month month,
        DayOfWeek dayOfWeek
) {}
