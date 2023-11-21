import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import strategies.SimpleMovingAverageStrategy;
import strategies.TradingStrategy;
import utils.AlphaVantageConnector;
import utils.DatabaseHelper;
import utils.MarketData;
import utils.TradeSignal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class Main {
    public static void main(String[] args) {
        String symbol = "AAPL"; // Example for Apple Inc
        DatabaseHelper dbHelper = new DatabaseHelper();
        String rawData;

        // Check if data is present in the database
        if (dbHelper.isDataPresent(symbol)) {
            rawData = dbHelper.getData(symbol);
            System.out.println("Using cached data from database.");
        } else {
            AlphaVantageConnector connector = new AlphaVantageConnector();
            rawData = connector.fetchData("TIME_SERIES_DAILY", symbol);
            dbHelper.insertOrUpdateData(symbol, rawData);
            System.out.println("Fetched data from Alpha Vantage and stored in database.");
        }

        List<MarketData> marketDataList = parseMarketData(rawData);
        TradingStrategy strategy = new SimpleMovingAverageStrategy(5); // 5-day moving average

        for (MarketData data : marketDataList) {
            TradeSignal signal = strategy.generateSignal(data);
            System.out.println("Date: " + data.getTimestamp() + ", Action: " + signal.getActionType() +
                    ", Price: " + signal.getPrice() + ", Quantity: " + signal.getQuantity());
        }
    }

    private static List<MarketData> parseMarketData(String rawData) {
        List<MarketData> data = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(rawData).getAsJsonObject();
        JsonObject timeSeries = jsonObject.getAsJsonObject("Time Series (Daily)");

        if (timeSeries != null) {
            for (Entry<String, JsonElement> entry : timeSeries.entrySet()) {
                String key = entry.getKey();
                JsonObject dayData = entry.getValue().getAsJsonObject();
                LocalDate date = LocalDate.parse(key, DateTimeFormatter.ISO_DATE);

                // Assuming you have a constructor in MarketData that accepts LocalDate
                MarketData marketData = new MarketData(
                        date.atStartOfDay(), // Convert LocalDate to LocalDateTime at the start of the day
                        dayData.get("1. open").getAsDouble(),
                        dayData.get("2. high").getAsDouble(),
                        dayData.get("3. low").getAsDouble(),
                        dayData.get("4. close").getAsDouble(),
                        dayData.get("5. volume").getAsLong()
                );
                data.add(marketData);
            }
        } else {
            System.out.println("No time series data found in the response.");
        }
        return data;
    }

}
