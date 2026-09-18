package calendar;

import java.time.Month;
import java.util.List;

public record CalendarMonth(
    Month month,
    List<CalendarDay> days
) {}
