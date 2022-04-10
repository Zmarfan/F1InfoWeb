package f1_Info.background.ergast_tasks.ergast.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import f1_Info.constants.Country;
import f1_Info.constants.Url;
import f1_Info.utils.DateUtils;
import lombok.Value;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Value
public class DriverData {
    String mDriverIdentifier;
    Url mWikipediaUrl;
    String mFirstName;
    String mLastName;
    Date mDateOfBirth;
    Country mCountry;
    Integer mPermanentNumber;
    String mCode;

    public DriverData(
        @JsonProperty("driverId") String driverIdentifier,
        @JsonProperty("url") String wikipediaUrl,
        @JsonProperty("givenName") String firstName,
        @JsonProperty("familyName") String lastName,
        @JsonProperty("dateOfBirth") String dateOfBirthString,
        @JsonProperty("nationality") String nationality,
        @JsonProperty("permanentNumber") Integer permanentNumber,
        @JsonProperty("code") String code
    ) throws ParseException, MalformedURLException {
        mDriverIdentifier = driverIdentifier;
        mWikipediaUrl = new Url(wikipediaUrl);
        mFirstName = firstName;
        mLastName = lastName;
        mDateOfBirth = DateUtils.parse(dateOfBirthString);
        mCountry = Country.fromNationality(nationality);
        mPermanentNumber = permanentNumber;
        mCode = code;
    }

    public Optional<Integer> getPermanentNumber() {
        return Optional.ofNullable(mPermanentNumber);
    }

    public Optional<String> getCode() {
        return Optional.ofNullable(mCode);
    }
}
