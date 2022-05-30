package database.sql_parsing;

import common.logger.Logger;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static database.ObjectNames.*;
import static java.util.Map.entry;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SqlParser<T> {
    private static final Map<String, Function<SqlParserInstance, Object>> SQL_MAPPING = Map.ofEntries(
        entry(INT, SqlParserFunctions::readInteger),
        entry(NULLABLE_INT, SqlParserFunctions::readInteger),
        entry(LONG, SqlParserFunctions::readLong),
        entry(NULLABLE_LONG, SqlParserFunctions::readLong),
        entry(STRING, SqlParserFunctions::readString),
        entry(DATE, SqlParserFunctions::readDate),
        entry(TIME, SqlParserFunctions::readTime),
        entry(BOOLEAN, SqlParserFunctions::readBoolean),
        entry(NULLABLE_BOOLEAN, SqlParserFunctions::readBoolean),
        entry(BIG_DECIMAL, SqlParserFunctions::readBigDecimal),
        entry(DOUBLE, SqlParserFunctions::readDouble),
        entry(NULLABLE_DOUBLE, SqlParserFunctions::readDouble),
        entry(COUNTRY, SqlParserFunctions::readCountry),
        entry(URL, SqlParserFunctions::readUrl),
        entry(EMAIL, SqlParserFunctions::readEmail)
    );

    private final Class<T> mRecordClass;
    private final ResultSet mResult;
    private final Logger mLogger;

    public List<T> parseRecordsList() throws IllegalArgumentException {
        try {
            final RecordParsingInfo recordParsingInfo = getRecordParsingInfo();

            final List<T> records = new ArrayList<>();
            while (mResult.next()) {
                records.add(readRecord(recordParsingInfo));
            }
            return records;
        } catch (final Exception e) {
            throw new IllegalArgumentException(String.format("Unable to parse query to the record: %s", mRecordClass.getName()), e);
        }
    }

    public List<T> parseBasicList() throws IllegalArgumentException {
        try {
            final List<T> values = new ArrayList<>();
            while (mResult.next()) {
                values.add((T) SQL_MAPPING.get(mRecordClass.getName()).apply(new SqlParserInstance(mResult, 1)));
            }
            return values;
        } catch (final SQLException e) {
            throw new IllegalArgumentException(String.format("Unable to parse query to the value: %s", mRecordClass.getName()), e);
        }
    }

    private T readRecord(final RecordParsingInfo recordParsingInfo) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final List<Object> parameterValues = extractParameters(recordParsingInfo.getParameters());
        return (T) recordParsingInfo.getConstructor().newInstance(parameterValues.toArray());
    }

    private RecordParsingInfo getRecordParsingInfo() {
        final Constructor<?> constructor = mRecordClass.getConstructors()[0];
        return new RecordParsingInfo(constructor, Arrays.stream(constructor.getParameters()).toList());
    }

    private List<Object> extractParameters(final List<Parameter> parameters) {
        int columnIndex = 1;

        final List<Object> values = new ArrayList<>();
        for (final Parameter parameter : parameters) {
            values.add(extractParameter(parameter, columnIndex++));
        }

        return values;
    }

    private Object extractParameter(final Parameter parameter, final int columnIndex) {
        try {
            return SQL_MAPPING.get(parameter.getType().getName()).apply(new SqlParserInstance(mResult, columnIndex));
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(
                "Unable to parse the parameter %s, to the type: %s at column index: %d",
                parameter.getName(),
                parameter.getType().getName(),
                columnIndex
            ), e);
        }
    }
}
