package common.constants;

import lombok.Value;

@Value
public class CountryCodes {
    String mIsoCode;
    String mIcoCode;

    public static CountryCodes fromCountry(final Country country) {
        return new CountryCodes(country.getCode(), country.getIOCCode());
    }
}
