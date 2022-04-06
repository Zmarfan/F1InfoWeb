package f1_Info.database;

import f1_Info.logger.Logger;
import lombok.Getter;

import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Function;

@Getter
public class SqlParser<T> {
    private static final Map<String, Function<SqlParser<?>, Object>> SQL_MAPPING = Map.of(
        "int", StatementHelper::readInteger,
        "java.lang.Integer", StatementHelper::readInteger
    );

    private final Class<T> mRecordClass;
    private final ResultSet mResult;
    private final Logger mLogger;
    private int mColumnIndex = 1;

    public SqlParser(Class<T> recordClass, ResultSet result, Logger logger) {
        mRecordClass = recordClass;
        mResult = result;
        mLogger = logger;
    }

    public T parseBasic() {
        return (T)SQL_MAPPING.get(mRecordClass.getName()).apply(this);
    }
}
