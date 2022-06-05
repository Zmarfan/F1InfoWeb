package f1_Info.background.ergast_tasks.ergast.responses.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.utils.DateUtils;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
public
class ErgastSessionTimes {
    LocalDate mDate;
    LocalTime mTime;

    public ErgastSessionTimes(
        @JsonProperty("date") String dateString,
        @JsonProperty("time") String timeString
    ) {
        mDate = dateString != null ? LocalDate.parse(dateString) : null;
        mTime = timeString != null ? DateUtils.parseTime(timeString) : null;
    }
}
