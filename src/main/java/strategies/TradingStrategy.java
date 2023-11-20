package strategies;

import utils.MarketData;
import utils.TradeSignal;

public interface TradingStrategy {
    TradeSignal generateSignal(MarketData data);

}
