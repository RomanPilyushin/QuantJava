package data;

import utils.ConfigReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AlphaVantageConnector {

    private final String API_KEY;
    private static final String BASE_URL = "https://www.alphavantage.co/query?";

    // Constructor with API Key as a parameter
    public AlphaVantageConnector(String apiKey) {
        this.API_KEY = apiKey;
    }

    // Constructor that uses ConfigReader to get the API Key
    public AlphaVantageConnector() {
        ConfigReader configReader = new ConfigReader();
        this.API_KEY = configReader.getProperty("alphaVantage.apiKey");
    }

    public String fetchData(String function, String symbol) {
        String urlString = BASE_URL + "function=" + function + "&symbol=" + symbol + "&apikey=" + API_KEY;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();
                return inline.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String fetchIntradayData(String symbol, String interval) {
        return fetchData("TIME_SERIES_INTRADAY", symbol, "&interval=" + interval);
    }

    private String fetchData(String function, String symbol, String additionalParams) {
        String urlString = BASE_URL + "function=" + function + "&symbol=" + symbol + additionalParams + "&apikey=" + API_KEY;
        // Rest of the implementation...
        return urlString;
    }
}

