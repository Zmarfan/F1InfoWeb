package f1_Info.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class StringUtil {
    public static String camelCaseToSnakeCase(final String camelCase) {
        final Matcher patternMatcher = Pattern.compile("[A-Z]").matcher(camelCase);
        final StringBuilder buffer = new StringBuilder();
        while (patternMatcher.find()) {
            patternMatcher.appendReplacement(buffer, "_" + patternMatcher.group().toLowerCase());
        }
        patternMatcher.appendTail(buffer);
        return buffer.toString();
    }
}
