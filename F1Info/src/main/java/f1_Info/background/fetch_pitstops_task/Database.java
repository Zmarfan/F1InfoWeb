package f1_Info.background.fetch_pitstops_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.pit_stop.PitStopData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component(value = "FetchPitStopsTaskDatabase")
public class Database extends TaskDatabase {

    private static final int FIRST_SEASON_WITH_PISTOP_DATA = 2011;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<PitStopFetchInformationRecord> getNextSeasonAndRoundToFetchPitStopsFor() throws SQLException {
        return Optional.ofNullable(executeQuery(new GetNextRaceToFetchPitStopsForQueryData(FIRST_SEASON_WITH_PISTOP_DATA)));
    }

    public void mergeIntoPitStopsData(final List<PitStopData> pitStopDataList, final PitStopFetchInformationRecord fetchInformation) throws SQLException {

    }
}