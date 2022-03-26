package f1_Info.constants;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.validator.routines.UrlValidator;

import java.net.MalformedURLException;

@Getter
@EqualsAndHashCode
public class Url {
    private final String url;

    public Url(String url) throws MalformedURLException {
        final UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url)) {
            throw new MalformedURLException(String.format("The given url %s is not a valid url!", url));
        }
        this.url = url;
    }
}
