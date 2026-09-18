import calendar.CalendarService;
import calendar.CalendarYear;

public class Main {
    public static void main(String[] args) {

        CalendarService calendarService = new CalendarService();
        CalendarYear calendarYear = calendarService.getCalendar(1700);
        CalendarService.formatCalendar(calendarYear);

    }
}