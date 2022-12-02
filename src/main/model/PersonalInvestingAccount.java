package model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Citation: Some code is modeled after JsonSerializationDemo.
// Represents an investing account that has a balance, value, amount of money deposited, as well as lists of
// stocks purchased in the account, their associated number of shares purchased and stock values
public class PersonalInvestingAccount implements Writable {
    private double cashBalance;
    private double accountValue;
    private double depositAmount;
    private List<Stock> stocksPurchased;
    private List<Integer> stocksNumSharesPurchased;
    private List<Double> stockValues;

    // EFFECTS: instantiates a personal investing account with starting balance, value and depositAmount, and
    // list of stocks and associated purchase prices
    public PersonalInvestingAccount() {
        this.cashBalance = 0;
        this.accountValue = 0;
        this.depositAmount = 0;
        this.stocksPurchased = new ArrayList<>();
        this.stocksNumSharesPurchased = new ArrayList<>();
        this.stockValues = new ArrayList<>();
    }

    // REQUIRES: amount > 0 and up to 2 decimal places
    // MODIFIES: this
    // EFFECTS: adds the inputted amount of money into the existing investing account while increasing the total
    // amount of money deposited
    public void depositMoney(double amount) {
        double amountRounded = Math.round(amount * 100.0) / 100.0;
        this.cashBalance += amountRounded;
        this.depositAmount += amountRounded;
        EventLog.getInstance().logEvent(new Event("$" + amountRounded + " deposited to account."));
    }

    // Citation: https://intellipaat.com/community/35143/how-to-round-up-to-2-decimal-places-in-java
    // This website helped me learn how to round doubles to 2 decimal places, in order to fit the amount of decimal
    // places money has.
    // REQUIRES: numShares > 0, balance >= purchasePrice
    // MODIFIES: this
    // EFFECTS: adds number of shares of stock into the account's list of stocks if not already present, with
    // updated stock's share amount, value in the account and account balance
    public void purchaseStock(Stock s, int numShares) {
        double purchasePriceRounded = Math.round((s.getPrice() * numShares) * 100.0) / 100.0;
        if (stocksPurchased.size() == 0) {
            this.stocksPurchased.add(s);
            this.stockValues.add(purchasePriceRounded);
            this.stocksNumSharesPurchased.add(numShares);
        } else {
            for (int i = 0; i < stocksPurchased.size(); i++) {
                if (s.getTicker().equals(stocksPurchased.get(i).getTicker())) {
                    int updatedNumShares = this.stocksNumSharesPurchased.get(i).intValue() + numShares;
                    this.stocksNumSharesPurchased.set(i, updatedNumShares);
                    double updatedStockValue = this.stockValues.get(i).doubleValue() + purchasePriceRounded;
                    this.stockValues.set(i, updatedStockValue);
                } else {
                    this.stocksPurchased.add(s);
                    this.stockValues.add(purchasePriceRounded);
                    this.stocksNumSharesPurchased.add(numShares);
                    break;
                }
            }
        }
        this.cashBalance = Math.round((this.cashBalance - purchasePriceRounded) * 100.00) / 100.00;
        EventLog.getInstance().logEvent(new Event(numShares + " " + s.getName() + " shares purchased at $"
                + s.getPrice() + " each."));
    }

    // REQUIRES: number of shares being sold <= number of shares of same stock currently in account
    // MODIFIES: this
    // EFFECTS: removes number of shares of stock out of the account's list of stocks, with balance updated
    public void sellStock(Stock s, int numShares) {
        double purchasePrice = s.getPrice() * numShares;
        double purchasePriceRounded = Math.round(purchasePrice * 100.0) / 100.0;
        for (int i = 0; i < this.stocksPurchased.size(); i++) {
            if (s.getTicker().equals(stocksPurchased.get(i).getTicker())) {
                if (this.stocksNumSharesPurchased.get(i) > numShares) {
                    int updatedNumShares = this.stocksNumSharesPurchased.get(i).intValue() - numShares;
                    this.stocksNumSharesPurchased.set(i, updatedNumShares);
                    this.cashBalance = Math.round((this.cashBalance + purchasePriceRounded) * 100.00) / 100.00;
                    break;
                } else if (this.stocksNumSharesPurchased.get(i) == numShares) {
                    this.stocksNumSharesPurchased.remove(i);
                    this.stocksPurchased.remove(i);
                    this.cashBalance = Math.round((this.cashBalance + purchasePriceRounded) * 100.00) / 100.00;
                    break;
                } else {
                    break;
                }
            }
        }
        EventLog.getInstance().logEvent(new Event(numShares + " " + s.getName() + " shares sold at $"
                + s.getPrice() + " each."));
    }

    // getters
    public double getCashBalance() {
        return this.cashBalance;
    }

    public double getAccountValue() {
        return this.accountValue;
    }

    public double getDepositAmount() {
        return this.depositAmount;
    }

    public List<Stock> getStocksPurchased() {
        return this.stocksPurchased;
    }

    public List<Integer> getStocksNumSharesPurchased() {
        return this.stocksNumSharesPurchased;
    }

    public List<Double> getStockValues() {
        return this.stockValues;
    }

    // setters
    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    public void setAccountValue(double accountValue) {
        this.accountValue = accountValue;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cashBalance", cashBalance);
        json.put("accountValue", accountValue);
        json.put("depositAmount", depositAmount);
        json.put("stocksPurchased", stocksPurchasedToJson());
        json.put("stocksNumSharesPurchased", stocksNumSharesPurchasedToJson());
        json.put("stockValues", stockValuesToJson());
        return json;
    }

    // EFFECTS: returns list of stocks purchased as a JSON array
    private JSONArray stocksPurchasedToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Stock s : stocksPurchased) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns list of each stock's number of shares purchased as a JSON array
    private JSONArray stocksNumSharesPurchasedToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Integer i : stocksNumSharesPurchased) {
            jsonArray.put(i);
        }
        return jsonArray;
    }

    // EFFECTS: returns list of purchased stock values as a JSON array
    private JSONArray stockValuesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Double d : stockValues) {
            jsonArray.put(d);
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: helper method for JsonWriter to add stocks into purchasedStock list
    public void addStocksPurchased(Stock s) {
        this.stocksPurchased.add(s);
    }

    // MODIFIES: this
    // EFFECTS: helper method for JsonWriter to add num shares of stock into stocksNumSharesPurchased list
    public void addStocksNumSharesPurchased(Integer num) {
        this.stocksNumSharesPurchased.add(num);
    }

    // MODIFIES: this
    // EFFECTS: helper method for JsonWriter to add stock values into stockValues list
    public void addStockValues(Double value) {
        this.stockValues.add(value);
    }
}
