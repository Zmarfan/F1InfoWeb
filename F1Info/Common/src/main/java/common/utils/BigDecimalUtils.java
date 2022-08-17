package common.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class BigDecimalUtils {
    public static BigDecimal divide(final BigDecimal value1, final BigDecimal value2) {
        return value1.divide(value2, 3, RoundingMode.HALF_UP);
    }
}
