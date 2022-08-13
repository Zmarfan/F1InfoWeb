package f1_Info.entry_points.reports.commands.get_standings_report_filter_values_command.constructor;

import lombok.Value;

@Value
public class ConstructorEntry {
    String mConstructorIdentifier;
    String mName;

    public ConstructorEntry(final ConstructorFromSeasonRecord constructorRecord) {
        mConstructorIdentifier = constructorRecord.getConstructorIdentifier();
        mName = constructorRecord.getName();
    }
}
