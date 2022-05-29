package f1_Info.background.ergast_tasks.ergast.responses.lap_times;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.utils.DateUtils;
import lombok.Value;

import java.math.BigDecimal;
import java.text.ParseException;

@Value
public class TimingData {
    String mDriverIdentification;
    int mPosition;
    String mFormattedTime;
    BigDecimal mTimeInSeconds;

    public TimingData(
        @JsonProperty("driverId") String driverIdentification,
        @JsonProperty("position") int position,
        @JsonProperty("time") String formattedTime
    ) throws ParseException {
        mDriverIdentification = driverIdentification;
        mPosition = position;
        mFormattedTime = formattedTime;
        mTimeInSeconds = DateUtils.parseTimeToSeconds(formattedTime);
    }
}
