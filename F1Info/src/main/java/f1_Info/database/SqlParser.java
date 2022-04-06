package f1_Info.database;

import f1_Info.logger.Logger;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Getter
public class SqlParser<T> {
    private static final Map<String, Function<SqlParserInstance, Object>> SQL_MAPPING = Map.of(
        "int", StatementHelper::readInteger,
        "java.lang.Integer", StatementHelper::readInteger
    );

    private final Class<T> mRecordClass;
    private final ResultSet mResult;
    private final Logger mLogger;

    public SqlParser(Class<T> recordClass, ResultSet result, Logger logger) {
        mRecordClass = recordClass;
        mResult = result;
        mLogger = logger;
    }

    public T parseRecord() {
        try {
            mResult.next();

            final Constructor<?> constructor = mRecordClass.getConstructors()[0];
            final List<Parameter> parameters = Arrays.stream(constructor.getParameters()).toList();

            final List<Object> parameterValues = extractParameters(parameters);
            return (T)constructor.newInstance(parameterValues.toArray());
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public T parseBasic() {
        try {
            mResult.next();
            return (T)SQL_MAPPING.get(mRecordClass.getName()).apply(new SqlParserInstance(mResult, 1));
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<Object> extractParameters(final List<Parameter> parameters) {
        int columnCount = 1;

        final List<Object> values = new ArrayList<>();
        for (final Parameter parameter : parameters) {
            final Object value = SQL_MAPPING.get(parameter.getType().getName()).apply(new SqlParserInstance(mResult, columnCount));
            columnCount++;
            values.add(value);
        }

        return values;
    }
}
