package f1_Info.database;

import f1_Info.logger.Logger;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;

@UtilityClass
public class DatabaseUtil {
    private static final Set<String> I_DATABASE_QUERY_DATA_METHOD_NAMES = Set.of("getStoredProcedureName", "getResponseClass");

    public static <T> T executeQuery(
        final Connection connection,
        final IQueryData<T> queryData,
        final Function<SqlParser<T>, T> parseCallback,
        final Logger logger
    ) throws SQLException {
        final List<ValueWithType> sqlParameters = queryDataToSqlParameters(queryData);
        final String procedureCallString = createMySqlProcedureCall(queryData, sqlParameters);

        try (final CallableStatement statement = connection.prepareCall(procedureCallString)) {
            final boolean hasResult = prepareStatementAndExecute(queryData, logger, sqlParameters, statement);
            if (!hasResult) {
                return null;
            }

            try (final ResultSet result = statement.getResultSet()) {
                return result != null ? parseCallback.apply(new SqlParser<>(queryData.getResponseClass(), result, logger)) : null;
            }
        }
    }

    private static <T> boolean prepareStatementAndExecute(
        final IQueryData<T> queryData,
        final Logger logger,
        final List<ValueWithType> sqlParameters,
        final CallableStatement statement
    ) throws SQLException {
        setSqlColumns(statement, sqlParameters, logger);
        try {
            return statement.execute();
        } catch (final SQLException e) {
            final StringBuilder callParametersBuilder = new StringBuilder();
            for (final ValueWithType entry : sqlParameters) {
                callParametersBuilder.append(String.format("%s %s%n", entry.getTypeName(), entry.getData()));
            }
            logger.warning(
                "executeQuery",
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

    private static void setSqlColumns(final CallableStatement statement, final List<ValueWithType>  sqlParameters, final Logger logger) throws SQLException {
        int columnIndex = 1;
        for (final ValueWithType valueWithType : sqlParameters) {
            setColumn(statement, columnIndex, valueWithType.getTypeName(), valueWithType.getData(), logger);
            columnIndex++;
        }
    }

    private static void setColumn(
        final CallableStatement statement,
        final int columnIndex,
        final String typeName,
        final Object value,
        final Logger logger
    ) throws SQLException {
        try {
            switch (typeName) {
                case "long", "java.lang.Long" -> StatementHelper.setLong(statement, columnIndex, (Long) value);
                case "java.lang.String" -> StatementHelper.setString(statement, columnIndex, (String) value);
                case "int", "java.lang.Integer" -> StatementHelper.setInt(statement, columnIndex, (Integer) value);
                case "java.util.Date" -> StatementHelper.setDate(statement, columnIndex, (Date) value);
                case "java.sql.Time" -> StatementHelper.setTime(statement, columnIndex, (Time) value);
                case "boolean", "java.lang.Boolean" -> StatementHelper.setBoolean(statement, columnIndex, (Boolean) value);
                case "java.math.BigDecimal" -> statement.setBigDecimal(columnIndex, (BigDecimal) value);
                case "double", "java.lang.Double" -> StatementHelper.setDouble(statement, columnIndex, (Double) value);
                default -> logger.warning("setColumn", DatabaseUtil.class, String.format("Failed to set column %s of type %s", columnIndex, typeName));
            }
        } catch (final SQLException e) {
            logger.severe("setColumn", DatabaseUtil.class, String.format("Could not find column %s in sql query", columnIndex), e);
            throw e;
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
