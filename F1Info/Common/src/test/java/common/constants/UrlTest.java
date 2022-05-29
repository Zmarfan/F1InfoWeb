package common.constants;

import common.constants.Url;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlTest {

    @Test
    void should_return_same_url_as_passed_in_to_url_when_fetching() throws MalformedURLException {
        final String url = "https://valid-url.com/very-cool/12";
        assertEquals(url, new Url(url).getUrl());
    }

    @Test
    void should_throw_malformed_url_exception_if_url_has_bad_format() {
        assertThrows(MalformedURLException.class, () -> new Url("http//not-valid-url.com/really/bad"));
    }

    @Test
    void should_throw_malformed_url_exception_if_url_is_empty() {
        assertThrows(MalformedURLException.class, () -> new Url(""));
    }

    @Test
    void should_throw_malformed_url_exception_if_url_is_null() {
        assertThrows(MalformedURLException.class, () -> new Url(null));
    }
}
