package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor;

import f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.RaceEntry;
import lombok.Value;

import java.util.List;

@Value
public class ConstructorReportFilterResponse {
    List<ConstructorEntry> mConstructors;
    List<RaceEntry> mRaces;
    boolean mSeasonHasSprints;
}
