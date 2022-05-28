package f1_Info.constants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryTest {
    @Test
    void should_throw_illegal_argument_exception_if_parsing_invalid_country_code() {
        assertThrows(IllegalArgumentException.class, () -> Country.fromCountryCode("ål"));
    }

    @Test
    void should_throw_illegal_argument_exception_if_parsing_null_country_code() {
        assertThrows(IllegalArgumentException.class, () -> Country.fromCountryCode(null));
    }

    @Test
    void should_throw_illegal_argument_exception_if_parsing_invalid_country_name() {
        assertThrows(IllegalArgumentException.class, () -> Country.fromName("swerige"));
    }

    @Test
    void should_throw_illegal_argument_exception_if_parsing_null_country_name() {
        assertThrows(IllegalArgumentException.class, () -> Country.fromName(null));
    }

    @Test
    void should_throw_illegal_argument_exception_if_parsing_invalid_nationality() {
        assertThrows(IllegalArgumentException.class, () -> Country.fromNationality("swensk"));
    }

    @Test
    void should_throw_illegal_argument_exception_if_parsing_null_nationality() {
        assertThrows(IllegalArgumentException.class, () -> Country.fromNationality(null));
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    void should_parse_country_code_to_country(final Country country) {
        assertEquals(country, Country.fromCountryCode(country.getCode()));
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    void should_parse_names_to_country(final Country country) {
        final long correctParses = country.getNames().stream().filter(name -> Country.fromName(name).equals(country)).count();
        assertEquals(country.getNames().size(), correctParses);
    }

    @ParameterizedTest
    @EnumSource(Country.class)
    void should_parse_nationalities_to_country(final Country country) {
        final long correctParses = country.getNationalityKeywords().stream().filter(nationality -> Country.fromNationality(nationality).equals(country)).count();
        assertEquals(country.getNationalityKeywords().size(), correctParses);
    }
}
