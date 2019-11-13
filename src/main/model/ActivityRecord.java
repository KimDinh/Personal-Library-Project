package model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class ActivityRecord implements Loadable, Saveable {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private Map<Calendar, List<String>> record;

    public ActivityRecord() {
        record = new TreeMap<>();
    }

    public void addActivity(String content, Clock clock) {
        Calendar date = getDateFromClock(clock);
        List<String> dateRecord = record.get(date);
        if (dateRecord != null) {
            dateRecord.add(content);
        } else {
            dateRecord = new ArrayList<>();
            dateRecord.add(content);
            record.put(date, dateRecord);
        }
    }

    // EFFECTS: return a Set of Calendar key in record
    public Set<Calendar> getSetOfDate() {
        return record.keySet();
    }

    // EFFECTS: it record contains date, return the activity in given in record;
    // otherwise return null
    public List<String> getActivityByDate(Calendar date) {
        if (record.containsKey(date)) {
            return record.get(date);
        }
        return null;
    }

    @Override
    public void load(Scanner inFile) {
        while (inFile.hasNext()) {
            Calendar date = parseDate(inFile.nextLine());
            int num = Integer.parseInt(inFile.nextLine());
            List<String> dateRecord = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                dateRecord.add(inFile.nextLine());
            }
            record.put(date, dateRecord);
        }
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        Set<Calendar> dateSet = record.keySet();
        for (Calendar date : dateSet) {
            outFile.write(ActivityRecord.DATE_FORMAT.format(date.getTime()) + "\n");
            List<String> dateRecord = record.get(date);
            outFile.write(dateRecord.size() + "\n");
            for (String s : dateRecord) {
                outFile.write(s + "\n");
            }
        }
    }

    // EFFECTS: return a Calendar with the date represented by the clock
    public static Calendar getDateFromClock(Clock clock) {
        Instant instant = Instant.now(clock);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        Calendar current = GregorianCalendar.from(zdt);
        return new GregorianCalendar(current.get(Calendar.YEAR), current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH));
    }

    // EFFECTS: return a Calendar with the date represented by dateString
    public static Calendar parseDate(String dateString) {
        Date date = ActivityRecord.DATE_FORMAT.parse(dateString, new ParsePosition(0));
        Calendar ret = new GregorianCalendar();
        ret.setTime(date);
        return ret;
    }
}
