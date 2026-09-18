package calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class CalendarService {

    private final Map<String, CalendarYear> cache = new HashMap<>();
    private static final Map<Month, String> MONTH_NAMES = new EnumMap<>(Month.class);
    private static final Map<DayOfWeek, String> DAY_NAMES = new EnumMap<>(DayOfWeek.class);

    static {
        MONTH_NAMES.put(Month.JANUARY, "Январь");
        MONTH_NAMES.put(Month.FEBRUARY, "Февраль");
        MONTH_NAMES.put(Month.MARCH, "Март");
        MONTH_NAMES.put(Month.APRIL, "Апрель");
        MONTH_NAMES.put(Month.MAY, "Май");
        MONTH_NAMES.put(Month.JUNE, "Июнь");
        MONTH_NAMES.put(Month.JULY, "Июль");
        MONTH_NAMES.put(Month.AUGUST, "Август");
        MONTH_NAMES.put(Month.SEPTEMBER, "Сентябрь");
        MONTH_NAMES.put(Month.OCTOBER, "Октябрь");
        MONTH_NAMES.put(Month.NOVEMBER, "Ноябрь");
        MONTH_NAMES.put(Month.DECEMBER, "Декабрь");

        DAY_NAMES.put(DayOfWeek.MONDAY, "ПН");
        DAY_NAMES.put(DayOfWeek.TUESDAY, "ВТ");
        DAY_NAMES.put(DayOfWeek.WEDNESDAY, "СР");
        DAY_NAMES.put(DayOfWeek.THURSDAY, "ЧТ");
        DAY_NAMES.put(DayOfWeek.FRIDAY, "ПТ");
        DAY_NAMES.put(DayOfWeek.SATURDAY, "СБ");
        DAY_NAMES.put(DayOfWeek.SUNDAY, "ВС");
    }

    private CalendarYear build(int year) {
        LocalDate firstDate = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, 31);

        List<CalendarMonth> months = new ArrayList<>();
        List<CalendarDay> days = new ArrayList<>();

        LocalDate current = firstDate;
        while (!current.isAfter(endDate)) {
            days.add(new CalendarDay(current.getDayOfMonth(), current.getMonth(), current.getDayOfWeek()));

            if (current.getMonthValue() != current.plusDays(1).getMonthValue()) {
                months.add(new CalendarMonth(current.getMonth(), days));
                days = new ArrayList<>();
            }

            current = current.plusDays(1);
        }

        CalendarYear res = new CalendarYear(firstDate.isLeapYear(), months);
        cache.put(cacheKey(firstDate), res);
        return res;
    }

    private String cacheKey(LocalDate date) {
        return date.getDayOfWeek().getValue() + "_" + date.isLeapYear();
    }

    private static String formatWeekHeader() {
        StringBuilder sb = new StringBuilder();
        for (DayOfWeek d : DayOfWeek.values()) {
            sb.append(DAY_NAMES.get(d)).append(" ");
        }
        return sb.toString();
    }

    private static String formatDays(List<CalendarDay> days) {
        StringBuilder res = new StringBuilder();

        int offset = days.get(0).dayOfWeek().getValue() - 1; //количество отступов для первой строки
        res.append("   ".repeat(offset));

        for (int i = 0; i < days.size(); i++) {
            CalendarDay day = days.get(i);
            res.append(day.day()).append(day.day() < 10 ? "  " : " "); //формируем ячейки если цифра двухзначная 1 отступ иначе 2

            if (day.dayOfWeek() == DayOfWeek.SUNDAY && i < days.size() - 1) {
                res.append("\n");
            }
        }
        return res.toString();
    }

    public static void formatCalendar(CalendarYear calendarYear) {
        calendarYear.months().forEach(month -> {
            System.out.println(MONTH_NAMES.get(month.month()));
            System.out.println(formatWeekHeader());
            System.out.println(formatDays(month.days()));
            System.out.println();
        });
    }

    public CalendarYear getCalendar(int year) {
        if (year <= 1600) {
            throw new IllegalArgumentException("Год должен быть больше 1600");
        }
        LocalDate firstDate = LocalDate.of(year, Month.JANUARY, 1);
        String cacheKey = cacheKey(firstDate);
        CalendarYear calendarYear;
        if (cache.containsKey(cacheKey)) {
            calendarYear = cache.get(cacheKey);
        } else {
            calendarYear = this.build(year);
        }
        return calendarYear;
    }

}
