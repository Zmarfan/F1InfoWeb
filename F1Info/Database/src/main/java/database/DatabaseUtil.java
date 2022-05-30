package database;

import common.constants.Country;
import common.constants.Email;
import common.constants.Url;
import common.logger.Logger;
import common.utils.ListUtils;
import database.sql_parsing.SqlParser;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static database.ObjectNames.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@UtilityClass
public class DatabaseUtil {
    private static final Set<String> I_DATABASE_QUERY_DATA_METHOD_NAMES = Arrays.stream(IQueryData.class.getDeclaredMethods())
        .map(Method::getName)
        .collect(toSet());

    public static <T> List<T> executeQuery(
        final Connection connection,
        final IQueryData<T> queryData,
        final Function<SqlParser<T>, List<T>> parseCallback,
        final Logger logger
    ) throws SQLException {
        try {
            final List<ValueWithType> sqlParameters = queryDataToSqlParameters(queryData);
            final String procedureCallString = createMySqlProcedureCall(queryData, sqlParameters);

            try (final CallableStatement statement = connection.prepareCall(procedureCallString)) {
                setSqlColumns(statement, sqlParameters);
                final boolean hasResult = executeQueryStatement(queryData, logger, sqlParameters, statement);
                if (!hasResult || parseCallback == null) {
                    return emptyList();
                }

                try (final ResultSet result = statement.getResultSet()) {
                    return result != null ? parseCallback.apply(new SqlParser<>(queryData.getRecordClass(), result, logger)) : emptyList();
                }
            }
        } catch (final Exception e) {
            logger.severe("executeQuery", DatabaseUtil.class, String.format("Unable to execute query for querydata: %s", queryData.toString()), e);
            throw new SQLException(e);
        }
    }

    public static void executeBulkQuery(
        final Connection connection,
        final List<? extends IQueryData<Void>> queryDataList,
        final Logger logger
    ) throws SQLException {
        try {
            final String procedureCallString = createMySqlProcedureCall(queryDataList.get(0), queryDataToSqlParameters(queryDataList.get(0)));
            try (final CallableStatement statement = connection.prepareCall(procedureCallString)) {
                for (final IQueryData<Void> queryData : queryDataList) {
                    setSqlColumns(statement, queryDataToSqlParameters(queryData));
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (final Exception e) {
            logger.severe("executeBulkQuery", DatabaseUtil.class, String.format(
                "Unable to execute %d bulk queries for querydata list: %s", queryDataList.size(), ListUtils.listToString(queryDataList, IQueryData::toString)
            ), e);
            throw new SQLException(e);
        }
    }

    private static <T> boolean executeQueryStatement(
        final IQueryData<T> queryData,
        final Logger logger,
        final List<ValueWithType> sqlParameters,
        final CallableStatement statement
    ) throws SQLException {
        try {
            return statement.execute();
        } catch (final SQLException e) {
            final StringBuilder callParametersBuilder = new StringBuilder();
            for (final ValueWithType entry : sqlParameters) {
                callParametersBuilder.append(String.format("%s %s%n", entry.getTypeName(), entry.getData()));
            }
            logger.warning(
                "executeQueryStatement",
                DatabaseUtil.class,
                String.format(
                    "There was a problem when calling the sql function %s using class %s. The function parameters :%n %s",
                    queryData.getStoredProcedureName(),
                    queryData.getClass(),
                    callParametersBuilder
                )
            );
            throw e;
        }
    }

    private static void setSqlColumns(final CallableStatement statement, final List<ValueWithType>  sqlParameters) throws SQLException {
        int columnIndex = 1;
        for (final ValueWithType valueWithType : sqlParameters) {
            setColumn(statement, columnIndex, valueWithType.getTypeName(), valueWithType.getData());
            columnIndex++;
        }
    }

    private static void setColumn(final CallableStatement statement, final int columnIndex, final String typeName, final Object value) throws SQLException {
        try {
            switch (typeName) {
                case LONG, NULLABLE_LONG -> StatementHelper.setLong(statement, columnIndex, (Long) value);
                case STRING -> StatementHelper.setString(statement, columnIndex, (String) value);
                case INT, NULLABLE_INT -> StatementHelper.setInt(statement, columnIndex, (Integer) value);
                case DATE -> StatementHelper.setDate(statement, columnIndex, (Date) value);
                case TIME -> StatementHelper.setTime(statement, columnIndex, (Time) value);
                case BOOLEAN, NULLABLE_BOOLEAN -> StatementHelper.setBoolean(statement, columnIndex, (Boolean) value);
                case BIG_DECIMAL -> statement.setBigDecimal(columnIndex, (BigDecimal) value);
                case DOUBLE, NULLABLE_DOUBLE -> StatementHelper.setDouble(statement, columnIndex, (Double) value);
                case COUNTRY -> StatementHelper.setString(statement, columnIndex, ((Country)value).getCode());
                case URL -> StatementHelper.setString(statement, columnIndex, ((Url)value).getUrl());
                case EMAIL -> StatementHelper.setString(statement, columnIndex, ((Email)value).read());
                default -> throw new SQLException(String.format("No parser exists to set column %s of type %s", columnIndex, typeName));
            }
        } catch (final SQLException e) {
            throw new SQLException(String.format("Unable to properly set column %s with index %d in sql query", typeName, columnIndex), e);
        }
    }

    private static <T> List<ValueWithType> queryDataToSqlParameters(final IQueryData<T> queryData) {
        return getDeclaredMethods(queryData.getClass())
            .map(method -> ValueWithType.fromMethod(queryData, method))
            .sorted(comparing(ValueWithType::getGetterName))
            .toList();
    }

    private static <T> String createMySqlProcedureCall(final IQueryData<T> queryData, final List<ValueWithType> sqlParameters) {
        final StringBuilder builder = new StringBuilder(String.format("{ call %s(", queryData.getStoredProcedureName()));

        final String procedureParameters = sqlParameters.stream().map(parameter -> "?").collect(joining(", "));
        builder.append(String.format("%s) }", procedureParameters));
        return builder.toString();
    }

    private static Stream<Method> getDeclaredMethods(final Class<?> klass) {
        return getClassMethods(klass)
            .stream()
            .filter(method -> !I_DATABASE_QUERY_DATA_METHOD_NAMES.contains(method.getName()))
            .filter(method -> method.getName().startsWith("get"));
    }

    private static List<Method> getClassMethods(final Class<?> klass) {
        final List<Method> fetchedMethods = new ArrayList<>();
        Class<?> traverser = klass;

        while(traverser != null && traverser != Object.class) {
            fetchedMethods.addAll(Arrays.asList(traverser.getDeclaredMethods()));
            traverser = traverser.getSuperclass();
        }

        return unmodifiableList(fetchedMethods);
    }
}
