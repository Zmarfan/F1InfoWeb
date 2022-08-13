package f1_Info.entry_points.reports.commands.get_constructor_report_commands.all;

import common.constants.CountryCodes;
import f1_Info.entry_points.reports.commands.get_driver_report_commands.all.PositionMove;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class OverviewConstructorReportResponse {
    int mPosition;
    PositionMove mPositionMove;
    String mName;
    CountryCodes mCountryCodes;
    BigDecimal mPoints;

    public OverviewConstructorReportResponse(final OverviewConstructorReportRecord reportRecord) {
        mPosition = reportRecord.getPosition();
        mPositionMove = reportRecord.getPositionMove();
        mName = reportRecord.getName();
        mCountryCodes = CountryCodes.fromCountry(reportRecord.getConstructorCountry());
        mPoints = reportRecord.getPoints();
    }
}
