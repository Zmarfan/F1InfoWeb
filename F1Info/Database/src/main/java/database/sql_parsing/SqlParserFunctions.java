package database.sql_parsing;

import common.constants.Country;
import common.constants.email.Email;
import common.constants.Url;
import common.constants.email.MalformedEmailException;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

@UtilityClass
public class SqlParserFunctions {
    public static Integer readInteger(final SqlParserInstance instance) {
        try {
            final int readInt = instance.getResultSet().getInt(instance.getColumnIndex());
            return instance.getResultSet().wasNull() ? null : readInt;
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Long readLong(final SqlParserInstance instance) {
        try {
            final long readLong = instance.getResultSet().getLong(instance.getColumnIndex());
            return instance.getResultSet().wasNull() ? null : readLong;
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String readString(final SqlParserInstance instance) {
        try {
            final String readString = instance.getResultSet().getString(instance.getColumnIndex());
            return instance.getResultSet().wasNull() ? null : readString;
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Date readDate(final SqlParserInstance instance) {
        try {
            final Date readDate = new Date(instance.getResultSet().getDate(instance.getColumnIndex()).getTime());
            return instance.getResultSet().wasNull() ? null : readDate;
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Time readTime(final SqlParserInstance instance) {
        try {
            final Time readTime = instance.getResultSet().getTime(instance.getColumnIndex());
            return instance.getResultSet().wasNull() ? null : readTime;
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Boolean readBoolean(final SqlParserInstance instance) {
        try {
            final boolean readBoolean = instance.getResultSet().getBoolean(instance.getColumnIndex());
            return instance.getResultSet().wasNull() ? null : readBoolean;
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static BigDecimal readBigDecimal(final SqlParserInstance instance) {
        try {
            final BigDecimal readBigDecimal = instance.getResultSet().getBigDecimal(instance.getColumnIndex());
            return instance.getResultSet().wasNull() ? null : readBigDecimal;
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Double readDouble(final SqlParserInstance instance) {
        try {
            final double readDouble = instance.getResultSet().getDouble(instance.getColumnIndex());
            return instance.getResultSet().wasNull() ? null : readDouble;
        } catch (final SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Country readCountry(final SqlParserInstance instance) {
        return Country.fromCountryCode(readString(instance));
    }

    public static Url readUrl(final SqlParserInstance instance) {
        try {
            return new Url(readString(instance));
        } catch (final MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Email readEmail(final SqlParserInstance instance) {
        try {
            return new Email(readString(instance));
        } catch (final MalformedEmailException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
