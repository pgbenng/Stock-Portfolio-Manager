package model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

public class PersonalInvestingAccount {
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
        this.cashBalance += amount;
        this.depositAmount += amount;
    }

    // Citation: https://intellipaat.com/community/35143/how-to-round-up-to-2-decimal-places-in-java
    // This website helped me learn how to round doubles to 2 decimal places, in order to fit the amount of decimal
    // places money has.
    // REQUIRES: numShares > 0, balance >= purchasePrice
    // MODIFIES: this
    // EFFECTS: adds number of shares of stock into the account's list of stocks if not already present, with
    // updated stock's share amount and value in the account
    public void purchaseStock(Stock s, int numShares) {
        double purchasePrice = s.getPrice() * numShares;
        double purchasePriceRounded = Math.round(purchasePrice * 100.0) / 100.0;
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
}
