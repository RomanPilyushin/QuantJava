package backtesting;

import strategies.TradingStrategy;
import utils.MarketData;
import utils.TradeSignal;

import java.util.List;

public class Backtester {
    public void runBacktest(TradingStrategy strategy, List<MarketData> marketData) {
        for (MarketData data : marketData) {
            TradeSignal signal = strategy.generateSignal(data);
            // Process the signal (buy, sell, hold)
        }
    }
}
