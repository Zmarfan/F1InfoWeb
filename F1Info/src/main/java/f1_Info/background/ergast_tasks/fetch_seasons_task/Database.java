package f1_Info.background.ergast_tasks.fetch_seasons_task;

import common.configuration.Configuration;
import common.logger.Logger;
import database.BulkOfWork;
import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.ergast.responses.SeasonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component(value = "FetchSeasonsTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void mergeIntoSeasonsData(final List<SeasonData> seasonDataList) throws SQLException {
        executeBulkOfWork(new BulkOfWork(seasonDataList.stream().map(MergeIntoSeasonQueryData::new).toList()));
    }
}

