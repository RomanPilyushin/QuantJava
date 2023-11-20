package utils;

import java.time.LocalDateTime;

public class MarketData {
    private final LocalDateTime timestamp;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final long volume;

    public MarketData(LocalDateTime timestamp, double open, double high, double low, double close, long volume) {
        this.timestamp = timestamp;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public long getVolume() {
        return volume;
    }

    // Add additional methods or fields specific to options trading if needed
}
