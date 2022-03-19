package f1_Info.ergast;

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
    private static final String FETCH_ALL_CONSTRUCTORS_URI = "http://ergast.com/api/f1/constructors.json?limit=5";
    private static final String ERROR_RESPONSE_MESSAGE = "Got response code: %d as reply with the message: %s";
    private static final String TEST_DATA = "{\n" +
            "    \"MRData\": {\n" +
            "        \"xmlns\": \"http://ergast.com/mrd/1.5\",\n" +
            "        \"series\": \"f1\",\n" +
            "        \"url\": \"http://ergast.com/api/f1/constructors.json\",\n" +
            "        \"limit\": \"5\",\n" +
            "        \"offset\": \"0\",\n" +
            "        \"total\": \"211\",\n" +
            "        \"ConstructorTable\": {\n" +
            "            \"Constructors\": [\n" +
            "                {\n" +
            "                    \"constructorId\": \"adams\",\n" +
            "                    \"url\": \"http://en.wikipedia.org/wiki/Adams_(constructor)\",\n" +
            "                    \"name\": \"Adams\",\n" +
            "                    \"nationality\": \"American\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"constructorId\": \"afm\",\n" +
            "                    \"url\": \"http://en.wikipedia.org/wiki/Alex_von_Falkenhausen_Motorenbau\",\n" +
            "                    \"name\": \"AFM\",\n" +
            "                    \"nationality\": \"German\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"constructorId\": \"ags\",\n" +
            "                    \"url\": \"http://en.wikipedia.org/wiki/Automobiles_Gonfaronnaises_Sportives\",\n" +
            "                    \"name\": \"AGS\",\n" +
            "                    \"nationality\": \"French\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"constructorId\": \"alfa\",\n" +
            "                    \"url\": \"http://en.wikipedia.org/wiki/Alfa_Romeo_in_Formula_One\",\n" +
            "                    \"name\": \"Alfa Romeo\",\n" +
            "                    \"nationality\": \"Swiss\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"constructorId\": \"alphatauri\",\n" +
            "                    \"url\": \"http://en.wikipedia.org/wiki/Scuderia_AlphaTauri\",\n" +
            "                    \"name\": \"AlphaTauri\",\n" +
            "                    \"nationality\": \"Italian\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    }\n" +
            "}";

    private final Parser mParser;
    private final Logger mLogger;

    @Autowired
    public ErgastProxy(Parser mParser, Logger mLogger) {
        this.mParser = mParser;
        this.mLogger = mLogger;
    }

    public List<ConstructorData> fetchAllConstructors() {
        try {
            //final String responseJson = readDataAsJsonStringFromUri(FETCH_ALL_CONSTRUCTORS_URI);
            return mParser.parseConstructorsResponseToObjects(TEST_DATA);
        } catch (final Exception e) {
            mLogger.logError("Unable to fetch constructor data from ergast", e);
            return emptyList();
        }
    }

    private String readDataAsJsonStringFromUri(final String uri) throws IOException {
        try {
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
