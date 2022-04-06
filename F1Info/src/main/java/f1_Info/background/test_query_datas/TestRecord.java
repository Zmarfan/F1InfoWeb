package f1_Info.background.test_query_datas;

import f1_Info.constants.Country;
import f1_Info.constants.Url;
import lombok.Value;

import java.util.Date;

@Value
public class TestRecord {
    int mId;
    String mDriverIdentifier;
    Integer mNumber;
    String mCode;
    String mFirstName;
    String mLastName;
    Date mDateOfBirth;
    Country mCountry;
    Url mWikipediaPage;
}
