package f1_Info.database;

import lombok.Value;

import java.sql.ResultSet;

@Value
public class SqlParserInstance {
    ResultSet mResultSet;
    int mColumnIndex;
}
