package f1_Info.ergast;

import f1_Info.logger.Logger;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@AllArgsConstructor
public class ErgastProxy {
    private static final String GET_REQUEST = "GET";
    private static final int CONNECT_TIME_OUT = 10000;
    private static final int READ_TIME_OUT = 25000;
    private static final String FETCH_ALL_CONSTRUCTORS_URI = "http://ergast.com/api/f1/constructors?limit=250";
    private static final String ERROR_RESPONSE_MESSAGE = "Got response code: %d as reply";

    private final Logger mLogger;

    public void fetchAllConstructors() {
        final Optional<String> test = readDataAsJsonStringFromUri(FETCH_ALL_CONSTRUCTORS_URI);
        mLogger.logInfo(test.orElse("oh no error"));
    }

    private Optional<String> readDataAsJsonStringFromUri(final String uri) {
        try {
            final HttpURLConnection connection = createGetConnection(uri);
            final String data = readConnectionData(connection);
            connection.disconnect();
            return Optional.of(data);
        } catch (final IOException e) {
            mLogger.logError("Unable to read data from the uri: " + uri, e);
            return Optional.empty();
        }
    }

    private String readConnectionData(final HttpURLConnection connection) throws IOException {
        final BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        final StringBuilder content = new StringBuilder();
        while ((inputLine = streamReader.readLine()) != null) {
            content.append(inputLine);
        }
        streamReader.close();
        return content.toString();
    }

    private HttpURLConnection createGetConnection(final String uri) throws IOException {
        final URL url = new URL(uri);
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod(GET_REQUEST);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(CONNECT_TIME_OUT);
        connection.setReadTimeout(READ_TIME_OUT);

        if (connection.getResponseCode() > 299) {
            throw new IOException(String.format(ERROR_RESPONSE_MESSAGE, connection.getResponseCode()));
        }

        return connection;
    }
}
