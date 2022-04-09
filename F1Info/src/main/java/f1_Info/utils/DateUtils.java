package f1_Info.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@UtilityClass
public class DateUtils {
    public static Date parse(final String dateString) throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateString);
    }

    public static Time parseTime(final String isoTimestamp) throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat(isoTimestamp.endsWith("Z") ? "HH:mm:ss'Z'" : "HH:mm:ss");
        return new Time(formatter.parse(isoTimestamp).getTime());
    }

    public static BigDecimal parseTimeToSeconds(final String timeString) throws ParseException {
        try {
            final List<String> parts = Arrays.stream(timeString.split("(:)|(\\.)")).toList();
            return (BigDecimal.valueOf(Integer.parseInt(parts.get(0))).multiply(BigDecimal.valueOf(60)))
                .add(BigDecimal.valueOf(Integer.parseInt(parts.get(1))))
                .add(BigDecimal.valueOf(Integer.parseInt(parts.get(2))).divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP));
        } catch (final Exception e) {
            throw new ParseException(String.format("Unable to parse the time: %s to seconds", timeString), 0);
        }
    }
}
