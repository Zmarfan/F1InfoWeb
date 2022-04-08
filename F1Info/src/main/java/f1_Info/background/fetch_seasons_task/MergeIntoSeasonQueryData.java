package f1_Info.background.fetch_seasons_task;

import f1_Info.constants.Url;
import f1_Info.database.IQueryData;
import f1_Info.ergast.responses.SeasonData;
import lombok.Value;

@Value
public class MergeIntoSeasonQueryData implements IQueryData<Void> {
    int m1Season;
    Url m2Url;

    public MergeIntoSeasonQueryData(final SeasonData seasonData) {
        m1Season = seasonData.getYear();
        m2Url = seasonData.getWikipediaUrl();
    }

    @Override
    public String getStoredProcedureName() {
        return "tasks_insert_season_if_not_present";
    }
}
