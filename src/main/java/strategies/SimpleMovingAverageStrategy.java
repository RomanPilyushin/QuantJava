package strategies;

import utils.MarketData;
import utils.TradeSignal;
import utils.TradeSignal.ActionType;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleMovingAverageStrategy implements TradingStrategy {

    private final int period;
    private final Queue<Double> window;
    private double sum;

    public SimpleMovingAverageStrategy(int period) {
        if (period <= 0) {
            throw new IllegalArgumentException("Period must be greater than zero");
        }
        this.period = period;
        this.window = new LinkedList<>();
        this.sum = 0.0;
    }

    @Override
    public TradeSignal generateSignal(MarketData data) {
        updateWindow(data.getClose());
        double movingAverage = sum / period;

        if (data.getClose() > movingAverage) {
            return new TradeSignal(ActionType.BUY, data.getClose(), 1); // Buy signal
        } else if (data.getClose() < movingAverage) {
            return new TradeSignal(ActionType.SELL, data.getClose(), 1); // Sell signal
        }
        return new TradeSignal(ActionType.HOLD, data.getClose(), 0); // Hold signal
    }

    private void updateWindow(double price) {
        sum += price;
        window.add(price);
        if (window.size() > period) {
            sum -= window.poll();
        }
    }
}
