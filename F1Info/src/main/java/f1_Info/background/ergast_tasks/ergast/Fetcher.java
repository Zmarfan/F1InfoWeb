package f1_Info.background.ergast_tasks.ergast;

import com.google.common.util.concurrent.RateLimiter;
import f1_Info.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

@Component
public class Fetcher {
    private static final String GET_REQUEST = "GET";
    private static final int CONNECT_TIME_OUT = 10000;
    private static final int READ_TIME_OUT = 25000;
    private static final String TOO_MANY_REQUEST_PER_HOUR_MESSAGE = "Unable to make more requests since the maximum allowed %d requests per hour has been reached. Currently %d requests has been made this hour. There is %d minutes left until a request can be made";
    private static final String ERROR_RESPONSE_MESSAGE = "Got response code: %d as reply with the message: %s";
    private static final int MAX_REQUESTS_PER_HOUR = 200;
    private static final double RATE_LIMIT_PER_SECOND = 0.5;

    private final RateLimiter mRateLimiter = RateLimiter.create(RATE_LIMIT_PER_SECOND);
    private int mRequestCounterPerHour;
    private int mCountingHour = -1;

    public String readDataAsJsonStringFromUri(final String uri) throws IOException {
        try {
            if (hasMadeTooManyRequestThisHour()) {
                throw new IOException(String.format(
                    TOO_MANY_REQUEST_PER_HOUR_MESSAGE,
                    MAX_REQUESTS_PER_HOUR,
                    mRequestCounterPerHour,
                    DateUtils.getMinutesLeftInCurrentHour()
                ));
            }

            mRateLimiter.acquire();
            final HttpURLConnection connection = createGetConnection(uri);
            final String data = readConnectionData(connection);
            final int responseCode = connection.getResponseCode();
            connection.disconnect();

            if (responseHoldsErrorCode(connection)) {
                throw new IOException(String.format(ERROR_RESPONSE_MESSAGE, responseCode, data));
            }
            return data;
        } catch (final IOException e) {
            throw new IOException("Unable to read data from the uri: " + uri, e);
        }
    }

    private boolean hasMadeTooManyRequestThisHour() {
        final int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (currentHour != mCountingHour) {
            mCountingHour = currentHour;
            mRequestCounterPerHour = 1;
            return false;
        }

        return ++mRequestCounterPerHour > MAX_REQUESTS_PER_HOUR;
    }

    private HttpURLConnection createGetConnection(final String uri) throws IOException {
        final URL url = new URL(uri);
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod(GET_REQUEST);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(CONNECT_TIME_OUT);
        connection.setReadTimeout(READ_TIME_OUT);

        return connection;
    }

    private String readConnectionData(final HttpURLConnection connection) throws IOException {
        final BufferedReader streamReader = new BufferedReader(new InputStreamReader(
            !responseHoldsErrorCode(connection) ? connection.getInputStream() : connection.getErrorStream()
        ));
        String inputLine;
        final StringBuilder content = new StringBuilder();
        while ((inputLine = streamReader.readLine()) != null) {
            content.append(inputLine);
        }
        streamReader.close();
        return content.toString();
    }

    private boolean responseHoldsErrorCode(final HttpURLConnection connection) throws IOException {
        return connection.getResponseCode() > 299;
    }
}
