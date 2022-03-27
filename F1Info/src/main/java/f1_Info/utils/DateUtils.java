package f1_Info.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date parse(final String dateString) throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateString);
    }

    public static Time parseTime(final String isoTimestamp) throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss'Z'");
        return new Time(formatter.parse(isoTimestamp).getTime());
    }
}
