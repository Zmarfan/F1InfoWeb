package f1_Info.background.fetch_constructors_task;

import f1_Info.background.TaskDatabase;
import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static f1_Info.database.StatementHelper.setString;

@Component(value = "FetchConstructorsTaskDatabase")
public class Database extends TaskDatabase {

    @Autowired
    public Database(
        Configuration configuration,
        Logger logger
    ) {
        super(configuration, logger);
    }

    public void mergeIntoConstructorsData(final List<ConstructorData> constructorDataList) throws SQLException {
        try (final Connection connection = getConnection()) {
            for (final ConstructorData constructorData : constructorDataList) {
                if (!constructorDataAlreadyExistInDatabase(constructorData, connection)) {
                    insertConstructorDataEntry(constructorData, connection);
                }
            }
        }
    }

    private boolean constructorDataAlreadyExistInDatabase(final ConstructorData constructorData, final Connection connection) throws SQLException {
        try (final PreparedStatement preparedStatement = connection.prepareStatement("select id from constructors where constructor_identifier = ?;")) {
            preparedStatement.setString(1, constructorData.getConstructorIdentifier());
            return preparedStatement.executeQuery().next();
        }
    }

    private void insertConstructorDataEntry(final ConstructorData constructorData, final Connection connection) throws SQLException {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(
            "insert into constructors (constructor_identifier, name, country_code, wikipedia_page) values (?,?,?,?);"
        )) {
            preparedStatement.setString(1, constructorData.getConstructorIdentifier());
            preparedStatement.setString(2, constructorData.getName());
            setString(preparedStatement, 3, constructorData.getCountry().getCode());
            setString(preparedStatement, 4, constructorData.getWikipediaUrl().getUrl());
            preparedStatement.executeUpdate();
        }
    }
}