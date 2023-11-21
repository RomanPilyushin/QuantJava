package backtesting;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BacktestResult {
    private final double totalReturn;
    private final double maxDrawdown;
    private final double winLossRatio;

    public BacktestResult(double totalReturn, double maxDrawdown, double winLossRatio) {
        this.totalReturn = totalReturn;
        this.maxDrawdown = maxDrawdown;
        this.winLossRatio = winLossRatio;
    }
}
