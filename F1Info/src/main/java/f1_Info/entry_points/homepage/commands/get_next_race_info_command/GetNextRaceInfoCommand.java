package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static f1_Info.configuration.web.ResponseUtil.ok;
import static java.util.Collections.emptyList;

@AllArgsConstructor
public class GetNextRaceInfoCommand implements Command {
    private final TimeZone mTimeZone;
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final NextRaceInfoRecord info = mDatabase.getNextRaceInfo();
        return ok(new NextRaceInfoResponse(info.getRound(), info.getCountry().getCode(), info.getName(), createSessionInfos(info)));
    }

    private List<SessionInfo> createSessionInfos(final NextRaceInfoRecord info) {
        final ZoneId zoneId = ZoneId.of(info.getTimeZoneIdentifier());
        final List<SessionInfo> infos = new ArrayList<>();
        infos.addAll(formatSession(SessionType.FIRST_PRACTICE, info.getFp1Date(), info.getFp1Time(), zoneId, 60));
        infos.addAll(formatSession(SessionType.SECOND_PRACTICE, info.getFp2Date(), info.getFp2Time(), zoneId, 60));
        infos.addAll(formatSession(SessionType.SPRINT, info.getSprintDate(), info.getSprintTime(), zoneId, 60));
        infos.addAll(formatSession(SessionType.THIRD_PRACTICE, info.getFp3Date(), info.getFp3Time(), zoneId, 60));
        infos.addAll(formatSession(SessionType.QUALIFYING, info.getQualifyingDate(), info.getQualifyingTime(), zoneId, 120));
        infos.addAll(formatSession(SessionType.RACE, info.getRaceDate(), info.getRaceTime(), zoneId, 180));
        return infos;
    }

    private List<SessionInfo> formatSession(final SessionType sessionType, final LocalDate date, final LocalTime time, ZoneId trackTimeZoneId, final int endMinuteOffset) {
        if (date == null || time == null) {
            return emptyList();
        }
        final ZonedDateTime zonedDateTime = LocalDateTime.of(date, time).atZone(ZoneId.of("UTC"));
        final LocalDateTime myDateTime = zonedDateTime.withZoneSameInstant(mTimeZone.toZoneId()).toLocalDateTime();
        final LocalDateTime trackDateTime = zonedDateTime.withZoneSameInstant(trackTimeZoneId).toLocalDateTime();
        return List.of(new SessionInfo(sessionType, myDateTime.toString(), myDateTime.plusMinutes(endMinuteOffset).toString(), trackDateTime.toString()));
    }
}
