package f1_Info.entry_points.reports.commands.get_driver_report_filter_values_command;

import lombok.Value;

import java.util.List;

@Value
public class DriverReportFilterResponse {
    List<DriverEntry> mDrivers;
}
