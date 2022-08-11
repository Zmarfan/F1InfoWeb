package f1_Info.entry_points.reports.commands.get_race_report_commands.qualifying;

import common.constants.CountryCodes;
import lombok.Value;

@Value
public class QualifyingReportResponse {
    int mPosition;
    Integer mDriverNumber;
    String mDriver;
    CountryCodes mCountryCodes;
    String mConstructor;
    String mQ1;
    String mQ2;
    String mQ3;

    public QualifyingReportResponse(final QualifyingRecord qualifyingRecord) {
        mPosition = qualifyingRecord.getPosition();
        mDriverNumber = qualifyingRecord.getDriverNumber();
        mDriver = String.format("%s %s", qualifyingRecord.getFirstName(), qualifyingRecord.getLastName());
        mCountryCodes = CountryCodes.fromCountry(qualifyingRecord.getDriverCountry());
        mConstructor = qualifyingRecord.getConstructor();
        mQ1 = qualifyingRecord.getQ1();
        mQ2 = qualifyingRecord.getQ2();
        mQ3 = qualifyingRecord.getQ3();
    }
}
