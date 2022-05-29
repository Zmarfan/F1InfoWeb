package database.sql_parsing;

import lombok.Value;

import java.sql.ResultSet;

@Value
public class SqlParserInstance {
    ResultSet mResultSet;
    int mColumnIndex;
}
