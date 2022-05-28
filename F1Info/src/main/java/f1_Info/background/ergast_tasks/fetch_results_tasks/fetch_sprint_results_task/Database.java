package f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_sprint_results_task;

import f1_Info.background.TaskDatabase;
import f1_Info.background.ergast_tasks.ergast.responses.results.ResultDataHolder;
import f1_Info.background.ergast_tasks.fetch_results_tasks.MergeIntoResultsQueryData;
import f1_Info.configuration.Configuration;
import f1_Info.constants.f1.ResultType;
import f1_Info.database.BulkOfWork;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import static f1_Info.background.ergast_tasks.ErgastFetchingInformation.FIRST_SEASON_WITH_SPRINT_RESULTS_DATA;
import static f1_Info.wrappers.ThrowingFunction.wrapper;

@Component(value = "FetchSprintResultsTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public int getNextSeasonToFetchSprintResultsFor() throws SQLException {
        return executeBasicQuery(new GetNextSeasonToFetchSprintResultsForQueryData(FIRST_SEASON_WITH_SPRINT_RESULTS_DATA));
    }

    public void mergeIntoSprintResultsData(final List<ResultDataHolder> resultDataHolders) throws SQLException {
        try {
            executeBulkOfWork(new BulkOfWork(createMergeQueryDatasFromHolders(resultDataHolders)));
        } catch (final Exception e) {
            throw new SQLException(e);
        }
    }

    public void setLastFetchedSeason(final int lastFetchedSeason) throws SQLException {
        executeVoidQuery(new SetLastFetchedSeasonForSprintResultsFetchingQueryData(lastFetchedSeason));
    }

    private List<MergeIntoResultsQueryData> createMergeQueryDatasFromHolders(final List<ResultDataHolder> resultDatumHolders) {
        return resultDatumHolders
            .stream()
            .map(this::createMergeQueryDatasFromHolder)
            .flatMap(List::stream)
            .toList();
    }

    private List<MergeIntoResultsQueryData> createMergeQueryDatasFromHolder(final ResultDataHolder holder) {
        return holder.getSprintResultData()
            .stream()
            .map(wrapper(resultData -> new MergeIntoResultsQueryData(ResultType.SPRINT, resultData, holder.getSeason(), holder.getRound())))
            .toList();
    }
}
