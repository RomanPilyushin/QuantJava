package utils;

public class TradeSignal {
    public enum ActionType {
        BUY, SELL, HOLD
    }

    private final ActionType actionType;
    private final double price;
    private final int quantity;

    public TradeSignal(ActionType actionType, double price, int quantity) {
        this.actionType = actionType;
        this.price = price;
        this.quantity = quantity;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Add more fields or methods as needed
}

