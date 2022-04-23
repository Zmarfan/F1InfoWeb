package f1_Info.background.ergast_tasks.fetch_results_tasks.fetch_sprint_results_task;

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
import java.util.Optional;

import static f1_Info.background.ergast_tasks.ErgastFetchingInformation.FIRST_SEASON_WITH_SPRINT_RESULTS_DATA;
import static f1_Info.wrappers.ThrowingFunction.wrapper;

@Component(value = "FetchSprintResultsTaskDatabase")
public class Database extends TaskDatabase {
    private static final int NO_MORE_DATA_CAN_BE_FETCHED = -1;

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public Optional<Integer> getNextSeasonToFetchSprintResultsFor() throws SQLException {
        final int fetchedSeason = executeBasicQuery(new GetNextSeasonToFetchSprintResultsForQueryData(FIRST_SEASON_WITH_SPRINT_RESULTS_DATA));
        return Optional.of(fetchedSeason).filter(season -> season != NO_MORE_DATA_CAN_BE_FETCHED);
    }

    public void mergeIntoSprintResultsData(final List<ResultDataHolder> resultDatumHolders) throws SQLException {
        try {
            executeBulkOfWork(new BulkOfWork(createMergeQueryDatasFromHolders(resultDatumHolders)));
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
        return holder.getResultData()
            .stream()
            .map(wrapper(resultData -> new MergeIntoResultsQueryData(ResultType.SPRINT, resultData, holder.getSeason(), holder.getRound())))
            .toList();
    }
}
