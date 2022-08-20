package f1_Info.entry_points.drivers.commands.get_driver_chart_info_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static f1_Info.configuration.web.ResponseUtil.ok;

@AllArgsConstructor
public class GetDriverChartInfoCommand implements Command {
    private final String mDriverIdentifier;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final Map<Integer, List<BigDecimal>> pointsPerSeasons = mDatabase.getPointsPerSeason(mDriverIdentifier);

        return ok(new DriverChartInfoResponse(pointsPerSeasons));
    }
}
