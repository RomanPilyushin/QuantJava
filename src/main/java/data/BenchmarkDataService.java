package data;

public class BenchmarkDataService {
    private AlphaVantageConnector connector;
    private DatabaseHelper dbHelper;
    private static final String BENCHMARK_SYMBOL = "^GSPC"; // Symbol for S&P 500

    public BenchmarkDataService(String apiKey) {
        this.connector = new AlphaVantageConnector();
        this.dbHelper = new DatabaseHelper();
    }

    public String getBenchmarkData() {
        if (!dbHelper.isDataPresent(BENCHMARK_SYMBOL)) {
            String data = connector.fetchData("TIME_SERIES_DAILY", BENCHMARK_SYMBOL);
            dbHelper.insertOrUpdateData(BENCHMARK_SYMBOL, data);
        }
        return dbHelper.getData(BENCHMARK_SYMBOL);
    }
}

