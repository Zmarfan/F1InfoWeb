package f1_Info.entry_points.reports.commands.get_race_report_commands.qualifying;

import common.constants.CountryCodes;
import lombok.Value;

@Value
public class QualifyingReportResponse {
    String mDriverIdentifier;
    int mPosition;
    Integer mDriverNumber;
    String mDriver;
    CountryCodes mCountryCodes;
    String mConstructor;
    String mQ1;
    String mQ1Time;
    String mQ2;
    String mQ2Time;
    String mQ3;
    String mQ3Time;

    public QualifyingReportResponse(final QualifyingRecord qualifyingRecord) {
        mDriverIdentifier = qualifyingRecord.getDriverIdentifier();
        mPosition = qualifyingRecord.getPosition();
        mDriverNumber = qualifyingRecord.getDriverNumber();
        mDriver = String.format("%s %s", qualifyingRecord.getFirstName(), qualifyingRecord.getLastName());
        mCountryCodes = CountryCodes.fromCountry(qualifyingRecord.getDriverCountry());
        mConstructor = qualifyingRecord.getConstructor();
        mQ1 = formatDiff(qualifyingRecord.getQ1Diff(), qualifyingRecord.getQ1Time());
        mQ1Time = qualifyingRecord.getQ1Time();
        mQ2 = formatDiff(qualifyingRecord.getQ2Diff(), qualifyingRecord.getQ2Time());
        mQ2Time = qualifyingRecord.getQ2Time();
        mQ3 = formatDiff(qualifyingRecord.getQ3Diff(), qualifyingRecord.getQ3Time());
        mQ3Time = qualifyingRecord.getQ3Time();
    }

    private String formatDiff(final String diff, final String time) {
        if (diff == null) {
            return null;
        }
        if (Float.parseFloat(diff) == 0) {
            return time;
        }
        return String.format("+%s", diff);
    }
}
