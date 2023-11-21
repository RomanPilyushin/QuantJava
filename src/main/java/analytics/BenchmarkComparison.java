package analytics;

import utils.MarketData;
import java.util.List;

public class BenchmarkComparison {

    private List<MarketData> strategyData;
    private List<MarketData> benchmarkData;

    public BenchmarkComparison(List<MarketData> strategyData, List<MarketData> benchmarkData) {
        this.strategyData = strategyData;
        this.benchmarkData = benchmarkData;
    }

    public ComparisonResult comparePerformance() {
        double strategyReturn = calculateTotalReturn(strategyData);
        double benchmarkReturn = calculateTotalReturn(benchmarkData);
        return new ComparisonResult(strategyReturn, benchmarkReturn);
    }

    private double calculateTotalReturn(List<MarketData> data) {
        double startPrice = data.get(0).getClose();
        double endPrice = data.get(data.size() - 1).getClose();
        return (endPrice - startPrice) / startPrice;
    }

    public static class ComparisonResult {
        private final double strategyReturn;
        private final double benchmarkReturn;

        public ComparisonResult(double strategyReturn, double benchmarkReturn) {
            this.strategyReturn = strategyReturn;
            this.benchmarkReturn = benchmarkReturn;
        }

        public double getStrategyReturn() {
            return strategyReturn;
        }

        public double getBenchmarkReturn() {
            return benchmarkReturn;
        }

        @Override
        public String toString() {
            return "ComparisonResult{" +
                    "strategyReturn=" + strategyReturn +
                    ", benchmarkReturn=" + benchmarkReturn +
                    '}';
        }
    }
}
