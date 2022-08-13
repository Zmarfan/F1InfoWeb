package f1_Info.entry_points.reports.commands.get_driver_report_commands.all;

import common.constants.Country;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class AllDriverReportRecord {
    int mPosition;
    PositionMove mPositionMove;
    Integer mDriverNumber;
    String mFirstName;
    String mLastName;
    Country mDriverCountry;
    String mConstructor;
    BigDecimal mPoints;

    public AllDriverReportRecord(
        final int position,
        final Integer positionMoveId,
        final Integer driverNumber,
        final String firstName,
        final String lastName,
        final Country driverCountry,
        final String constructor,
        final BigDecimal points
    ) {
        mPosition = position;
        mPositionMove = positionMoveId != null ? PositionMove.fromId(positionMoveId) : null;
        mDriverNumber = driverNumber;
        mFirstName = firstName;
        mLastName = lastName;
        mDriverCountry = driverCountry;
        mConstructor = constructor;
        mPoints = points;
    }
}
