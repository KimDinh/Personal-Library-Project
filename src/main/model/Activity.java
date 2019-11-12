package model;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Activity {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private Calendar date;
    private String content;

    public Activity(String content) {
        this(content, Clock.systemDefaultZone());
    }

    public Activity(String content, Clock clock) {
        this.content = content;
        this.date = getDateFromClock(clock);
    }

    public static Calendar getDateFromClock(Clock clock) {
        Instant instant = Instant.now(clock);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        Calendar current = GregorianCalendar.from(zdt);
        return new GregorianCalendar(current.get(Calendar.YEAR), current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH));
    }
}
