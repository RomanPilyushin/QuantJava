package backtesting;

import strategies.TradingStrategy;
import utils.MarketData;
import utils.TradeSignal;

import java.util.List;

public class Backtester {
    private double initialPortfolioValue;
    private double slippageRate;
    private double transactionCost;

    public Backtester(double initialPortfolioValue, double slippageRate, double transactionCost) {
        this.initialPortfolioValue = initialPortfolioValue;
        this.slippageRate = slippageRate;
        this.transactionCost = transactionCost;
    }

    public BacktestResult runBacktest(TradingStrategy strategy, List<MarketData> marketDataList) {
        double portfolioValue = initialPortfolioValue;
        int winCount = 0;
        int lossCount = 0;
        double maxDrawdown = 0;
        double peak = initialPortfolioValue;

        // Iterate over market data
        for (MarketData data : marketDataList) {
            TradeSignal signal = strategy.generateSignal(data);
            // ... (trade execution logic)

            // Update metrics
            if (portfolioValue > peak) {
                peak = portfolioValue;
            }
            double drawdown = peak - portfolioValue;
            if (drawdown > maxDrawdown) {
                maxDrawdown = drawdown;
            }

            // Example win/loss count logic
            if (signal.getActionType() == TradeSignal.ActionType.BUY && data.getClose() > signal.getPrice()) {
                winCount++;
            } else if (signal.getActionType() == TradeSignal.ActionType.SELL && data.getClose() < signal.getPrice()) {
                winCount++;
            } else {
                lossCount++;
            }
        }

        // Calculate final metrics
        double totalReturn = (portfolioValue - initialPortfolioValue) / initialPortfolioValue;
        double winLossRatio = winCount / (double) lossCount;

        // Construct result summary
        return new BacktestResult(totalReturn, maxDrawdown, winLossRatio);
    }
}
