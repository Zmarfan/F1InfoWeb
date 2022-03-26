package f1_Info.ergast.responses.race;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.utils.DateUtils;
import lombok.Value;

import java.text.ParseException;
import java.util.Date;

@Value
class ErgastDate {
    Date date;

    public ErgastDate(@JsonProperty("date") String date) throws ParseException {
        this.date = DateUtils.parse(date);
    }
}
