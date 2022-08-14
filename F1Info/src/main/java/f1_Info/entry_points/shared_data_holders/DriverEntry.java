package f1_Info.entry_points.shared_data_holders;

import lombok.Value;

@Value
public class DriverEntry {
    String mDriverIdentifier;
    String mFullName;

    public DriverEntry(final DriverRecord driverRecord) {
        mDriverIdentifier = driverRecord.getDriverIdentifier();
        mFullName = String.format("%s %s", driverRecord.getFirstName(), driverRecord.getLastName());
    }
}
