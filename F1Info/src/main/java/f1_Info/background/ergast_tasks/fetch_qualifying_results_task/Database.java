package f1_Info.background.ergast_tasks.fetch_qualifying_results_task;

import common.configuration.Configuration;
import common.logger.Logger;
import database.BulkOfWork;
import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.ergast.responses.results.ResultDataHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import static common.wrappers.ThrowingFunction.wrapper;
import static f1_Info.background.ergast_tasks.ErgastFetchingInformation.FIRST_SEASON_WITH_QUALIFYING_RESULTS_DATA;

@Component(value = "FetchQualifyingResultsTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public int getNextSeasonToFetchQualifyingResultsFor() throws SQLException {
        return executeBasicQuery(new GetNextSeasonToFetchQualifyingResultsForQueryData(FIRST_SEASON_WITH_QUALIFYING_RESULTS_DATA));
    }

    public void mergeIntoQualifyingResultsData(final List<ResultDataHolder> resultDataHolders) throws SQLException {
        try {
            executeBulkOfWork(new BulkOfWork(createMergeQueryDatasFromHolders(resultDataHolders)));
        } catch (final Exception e) {
            throw new SQLException(e);
        }
    }

    public void setLastFetchedSeason(final int lastFetchedSeason) throws SQLException {
        executeVoidQuery(new SetLastFetchedSeasonForQualifyingResultsFetchingQueryData(lastFetchedSeason));
    }

    private List<MergeIntoQualifyingResultsQueryData> createMergeQueryDatasFromHolders(final List<ResultDataHolder> resultDatumHolders) {
        return resultDatumHolders
            .stream()
            .map(this::createMergeQueryDatasFromHolder)
            .flatMap(List::stream)
            .toList();
    }

    private List<MergeIntoQualifyingResultsQueryData> createMergeQueryDatasFromHolder(final ResultDataHolder holder) {
        return holder.getQualifyingResultData()
            .stream()
            .map(wrapper(resultData -> new MergeIntoQualifyingResultsQueryData(resultData, holder.getSeason(), holder.getRound())))
            .toList();
    }
}
