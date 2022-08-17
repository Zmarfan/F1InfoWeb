package f1_Info.entry_points.drivers.commands.get_driver_profile_command;

import common.constants.CountryCodes;
import common.utils.BigDecimalUtils;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Value
public class DriverProfileResponse {
    String mWikipediaTitle;
    String mWikipediaUrl;
    String mFullName;
    Long mAge;
    String mDateOfBirth;
    CountryCodes mCountryCodes;
    String mConstructor;
    Integer mAmountOfChampionships;
    Integer mAmountOfRunnerUpChampionships;
    String mFirstRaceName;
    String mLastRaceName;
    Integer mYearsInF1;
    Integer mRaceStarts;
    String mHighestFinish;
    String mHighestStartFinish;
    Integer mTeammates;
    Integer mLapsLed;
    Integer mAmountOfPodiums;
    Integer mAmountOfPitStops;
    Integer mAmountOfPoints;
    BigDecimal mPointsPerRace;
    BigDecimal mPointsPerSeason;
    Integer mLapsRaced;
    String mRetirements;
    String mDisqualifications;
    Integer mAverageFinishPosition;
    Integer mAverageStartPosition;

    public DriverProfileResponse(final DriverProfileRecord driverRecord) {
        mWikipediaTitle = driverRecord.getWikipediaUrl().substring(driverRecord.getWikipediaUrl().lastIndexOf("/") + 1);
        mWikipediaUrl = driverRecord.getWikipediaUrl();
        mFullName = String.format("%s %s", driverRecord.getFirstName(), driverRecord.getLastName());
        mAge = ChronoUnit.YEARS.between(driverRecord.getDateOfBirth(), LocalDate.now());
        mDateOfBirth = driverRecord.getDateOfBirth().toString();
        mCountryCodes = CountryCodes.fromCountry(driverRecord.getCountry());
        mConstructor = driverRecord.getConstructor() != null ? driverRecord.getConstructor() : "N/A";
        mAmountOfChampionships = driverRecord.getAmountOfChampionships();
        mAmountOfRunnerUpChampionships = driverRecord.getAmountOfRunnerUpChampionships();
        mFirstRaceName = driverRecord.getFirstRaceName();
        mLastRaceName = driverRecord.getLastRaceName();
        mYearsInF1 = driverRecord.getYearsInF1();
        mRaceStarts = driverRecord.getRaceStarts();
        mHighestFinish = valueWithAmount(driverRecord.getBestPosition(), driverRecord.getAmountOfBestPosition());
        mHighestStartFinish = valueWithAmount(driverRecord.getBestStartPosition(), driverRecord.getAmountOfBestStartPosition());
        mTeammates = driverRecord.getTeammates();
        mLapsLed = driverRecord.getLapsLed();
        mAmountOfPodiums = driverRecord.getAmountOfPodiums();
        mAmountOfPitStops = driverRecord.getAmountOfPitStops();
        mAmountOfPoints = driverRecord.getAmountOfPoints();
        mPointsPerRace = calculatePointsPerUnit(driverRecord.getAmountOfPoints(), driverRecord.getRaceStarts());
        mPointsPerSeason = calculatePointsPerUnit(driverRecord.getAmountOfPoints(), driverRecord.getYearsInF1());
        mLapsRaced = driverRecord.getLapsRaced();
        mRetirements = formatPercentageValues(driverRecord.getRetirements(), driverRecord.getRaceStarts());
        mDisqualifications = formatPercentageValues(driverRecord.getDisqualifications(), driverRecord.getRaceStarts());
        mAverageFinishPosition = driverRecord.getAverageFinishPosition();
        mAverageStartPosition = driverRecord.getAverageStartPosition();
    }

    private BigDecimal calculatePointsPerUnit(final Integer amountOfPoints, final Integer unit) {
        if (amountOfPoints == null || unit == null) {
            return null;
        }
        return BigDecimalUtils.divide(BigDecimal.valueOf(amountOfPoints), BigDecimal.valueOf(unit));
    }

    private String valueWithAmount(final Integer value, final Integer amount) {
        if (value == null || amount == null) {
            return null;
        }

        return String.format("%d (x%d)", value, amount);
    }

    private String formatPercentageValues(final Integer retirements, final Integer raceStarts) {
        if (retirements == null || raceStarts == null) {
            return null;
        }

        final BigDecimal ratio = BigDecimalUtils.divide(BigDecimal.valueOf(retirements), BigDecimal.valueOf(raceStarts));
        return String.format("%d (%s%%)", retirements, ratio.multiply(BigDecimal.valueOf(100)).stripTrailingZeros());
    }
}
