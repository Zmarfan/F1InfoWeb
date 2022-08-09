package f1_Info.entry_points.reports.commands.get_race_report_filter_values_command;

import lombok.Value;

import java.util.List;

@Value
public class RaceReportFilterResponse {
    List<RaceData> mCircuits;
}
