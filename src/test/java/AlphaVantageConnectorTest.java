

import utils.AlphaVantageConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AlphaVantageConnectorTest {

    @Test
    public void testFetchData() {
        AlphaVantageConnector connector = new AlphaVantageConnector();
        String result = connector.fetchData("TIME_SERIES_DAILY", "AAPL");
        assertNotNull("Data should not be null", result);
    }
}
