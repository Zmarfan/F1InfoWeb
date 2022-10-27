package f1_Info.entry_points.homepage.commands.get_next_race_info_command;

import f1_Info.entry_points.helper.Command;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static f1_Info.configuration.web.ResponseUtil.ok;
import static java.util.Collections.emptyList;

@AllArgsConstructor
public class GetNextRaceInfoCommand implements Command {
    private final Database mDatabase;

    @Override
    public ResponseEntity<?> execute() throws SQLException {
        final NextRaceInfoRecord info = mDatabase.getNextRaceInfo();
        return ok(new NextRaceInfoResponse(info.getRound(), info.getCountry().getCode(), info.getName(), createSessionInfos(info)));
    }

    private List<SessionInfo> createSessionInfos(final NextRaceInfoRecord info) {
        final List<SessionInfo> infos = new ArrayList<>();
        infos.addAll(formatSession(SessionType.FIRST_PRACTICE, info.getFp1Date(), info.getFp1Time(), 60));
        infos.addAll(formatSession(SessionType.SECOND_PRACTICE, info.getFp2Date(), info.getFp2Time(), 60));
        infos.addAll(formatSession(SessionType.SPRINT, info.getSprintDate(), info.getSprintTime(), 60));
        infos.addAll(formatSession(SessionType.THIRD_PRACTICE, info.getFp3Date(), info.getFp3Time(), 60));
        infos.addAll(formatSession(SessionType.QUALIFYING, info.getQualifyingDate(), info.getQualifyingTime(), 120));
        infos.addAll(formatSession(SessionType.RACE, info.getRaceDate(), info.getRaceTime(), 180));
        return infos;
    }

    private List<SessionInfo> formatSession(final SessionType sessionType, final LocalDate date, final LocalTime time, final int endMinuteOffset) {
        if (date == null || time == null) {
            return emptyList();
        }
        final ZonedDateTime zonedDateTime = LocalDateTime.of(date, time).atZone(ZoneId.of("UTC"));
        final LocalDateTime myDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        return List.of(new SessionInfo(sessionType, myDateTime.toString(), myDateTime.plusMinutes(endMinuteOffset).toString()));
    }
}
