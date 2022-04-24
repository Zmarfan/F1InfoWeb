package f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_race_results_task;

import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.ergast.responses.results.ResultDataHolder;
import f1_Info.background.ergast_tasks.fetch_results_tasks.MergeIntoResultsQueryData;
import f1_Info.configuration.Configuration;
import f1_Info.constants.ResultType;
import f1_Info.database.BulkOfWork;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import static f1_Info.background.ergast_tasks.ErgastFetchingInformation.FIRST_FORMULA_1_SEASON;
import static f1_Info.wrappers.ThrowingFunction.wrapper;

@Component(value = "FetchRaceResultsTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public int getNextSeasonToFetchRaceResultsFor() throws SQLException {
        return executeBasicQuery(new GetNextSeasonToFetchRaceResultsForQueryData(FIRST_FORMULA_1_SEASON));
    }

    public void mergeIntoRaceResultsData(final List<ResultDataHolder> resultDatumHolders) throws SQLException {
        try {
            executeBulkOfWork(new BulkOfWork(createMergeQueryDatasFromHolders(resultDatumHolders)));
        } catch (final Exception e) {
            throw new SQLException(e);
        }
    }

    public void setLastFetchedSeason(final int lastFetchedSeason) throws SQLException {
        executeVoidQuery(new SetLastFetchedSeasonForRaceResultsFetchingQueryData(lastFetchedSeason));
    }

    private List<MergeIntoResultsQueryData> createMergeQueryDatasFromHolders(final List<ResultDataHolder> resultDatumHolders) {
        return resultDatumHolders
            .stream()
            .map(this::createMergeQueryDatasFromHolder)
            .flatMap(List::stream)
            .toList();
    }

    private List<MergeIntoResultsQueryData> createMergeQueryDatasFromHolder(final ResultDataHolder holder) {
        return holder.getRaceResultData()
            .stream()
            .map(wrapper(resultData -> new MergeIntoResultsQueryData(ResultType.RACE, resultData, holder.getSeason(), holder.getRound())))
            .toList();
    }
}
