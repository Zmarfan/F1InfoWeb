package f1_Info.background.fetch_seasons_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.SeasonData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static f1_Info.database.StatementHelper.setString;

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
        try (final Connection connection = getConnection()) {
            for (final SeasonData seasonData : seasonDataList) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into seasons (year, wikipedia_page) values (?,?) on duplicate key update year = year;"
                )) {
                    preparedStatement.setInt(1, seasonData.getYear());
                    setString(preparedStatement, 2, seasonData.getWikipediaUrl().getUrl());
                    preparedStatement.executeUpdate();
                }
            }
        }
    }
}

