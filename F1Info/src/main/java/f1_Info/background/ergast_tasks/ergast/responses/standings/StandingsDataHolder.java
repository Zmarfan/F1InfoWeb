package f1_Info.background.ergast_tasks.ergast.responses.standings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingsDataHolder {
    List<DriverStandingsData> mDriverStandingsData;

    public StandingsDataHolder(
        @JsonProperty("DriverStandings") List<DriverStandingsData> driverStandingsData
    ) {
        mDriverStandingsData = driverStandingsData;
    }
}
