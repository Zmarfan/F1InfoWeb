package f1_Info.background.ergast_tasks.ergast.responses.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.utils.DateUtils;
import lombok.Value;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

@Value
public
class ErgastSessionTimes {
    Date mDate;
    Time mTime;

    public ErgastSessionTimes(
        @JsonProperty("date") String dateString,
        @JsonProperty("time") String timeString
    ) throws ParseException {
        mDate = dateString != null ? DateUtils.parse(dateString) : null;
        mTime = timeString != null ? DateUtils.parseTime(timeString) : null;
    }
}
