package calendar;

import java.util.List;

public record CalendarYear(
        boolean isLeapYear,
        List<CalendarMonth> months
) {
}
