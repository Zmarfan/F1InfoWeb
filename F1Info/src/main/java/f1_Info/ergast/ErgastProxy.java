package f1_Info.ergast;

import f1_Info.configuration.Configuration;
import f1_Info.ergast.responses.ConstructorData;
import f1_Info.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class ErgastProxy {
    private static final String GET_REQUEST = "GET";
    private static final int CONNECT_TIME_OUT = 10000;
    private static final int READ_TIME_OUT = 25000;
    private static final String FETCH_ALL_CONSTRUCTORS_URI = "http://ergast.com/api/f1/constructors.json?limit=%d";
    private static final int CONSTRUCTOR_LIMIT = 250;
    private static final String ERROR_RESPONSE_MESSAGE = "Got response code: %d as reply with the message: %s";

    private final Parser mParser;
    private final Configuration mConfiguration;
    private final Logger mLogger;

    @Autowired
    public ErgastProxy(Parser parser, Configuration configuration, Logger logger) {
        mParser = parser;
        mConfiguration = configuration;
        mLogger = logger;
    }

    public List<ConstructorData> fetchAllConstructors() {
        try {
            if (!mConfiguration.getRules().isMock()) {
                final String responseJson = readDataAsJsonStringFromUri(FETCH_ALL_CONSTRUCTORS_URI, CONSTRUCTOR_LIMIT);
                return mParser.parseConstructorsResponseToObjects(responseJson);
            }
        } catch (final Exception e) {
            mLogger.logError("fetchAllConstructors", ErgastProxy.class, "Unable to fetch constructor data from ergast", e);
        }
        return emptyList();
    }

    private String readDataAsJsonStringFromUri(final String uri, final int limit) throws IOException {
        try {
            final HttpURLConnection connection = createGetConnection(String.format(uri, limit));
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
