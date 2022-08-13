package f1_Info.entry_points.reports.commands.get_constructor_report_commands.all;

import common.constants.Country;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.all.PositionMove;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class OverviewConstructorReportRecord {
    int mPosition;
    PositionMove mPositionMove;
    String mName;
    Country mConstructorCountry;
    BigDecimal mPoints;

    public OverviewConstructorReportRecord(
        final int position,
        final Integer positionMoveId,
        final String name,
        final Country constructorCountry,
        final BigDecimal points
    ) {
        mPosition = position;
        mPositionMove = positionMoveId != null ? PositionMove.fromId(positionMoveId) : null;
        mName = name;
        mConstructorCountry = constructorCountry;
        mPoints = points;
    }
}
