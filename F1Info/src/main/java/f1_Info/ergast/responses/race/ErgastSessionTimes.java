package f1_Info.ergast.responses.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.utils.DateUtils;
import lombok.Value;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

@Value
public
class ErgastSessionTimes {
    Date date;
    Time time;

    public ErgastSessionTimes(
        @JsonProperty("date") String dateString,
        @JsonProperty("time") String timeString
    ) throws ParseException {
        this.date = dateString != null ? DateUtils.parse(dateString) : null;
        this.time = timeString != null ? DateUtils.parseTime(timeString) : null;
    }
}
