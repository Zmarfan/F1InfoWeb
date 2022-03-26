package f1_Info.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Country;
import f1_Info.utils.DateUtils;
import lombok.Value;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Value
public class DriverData {
    String driverIdentifier;
    String wikipediaUrl;
    String firstName;
    String lastName;
    Date dateOfBirth;
    Country country;
    Integer permanentNumber;
    String code;

    public DriverData(
        @JsonProperty("driverId") String driverIdentifier,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("givenName") String firstName,
        @JsonProperty("familyName") String lastName,
        @JsonProperty("dateOfBirth") String dateOfBirthString,
        @JsonProperty("nationality") String nationality,
        @JsonProperty("permanentNumber") Integer permanentNumber,
        @JsonProperty("code") String code
    ) throws ParseException {
        this.driverIdentifier = driverIdentifier;
        this.wikipediaUrl = wikipediaUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = DateUtils.parse(dateOfBirthString);
        this.country = Country.fromNationality(nationality);
        this.permanentNumber = permanentNumber;
        this.code = code;
    }

    public Optional<Integer> getPermanentNumber() {
        return Optional.ofNullable(permanentNumber);
    }

    public Optional<String> getCode() {
        return Optional.ofNullable(code);
    }
}
