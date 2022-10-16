package model;

public class Stock {
    private String name;
    private String ticker;
    private double price;

    // EFFECTS: instantiates a stock with inputted name, ticker and current price
    public Stock(String name, String ticker, double price) {
        this.name = name;
        this.ticker = ticker;
        this.price = price;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public String getTicker() {
        return this.ticker;
    }

    public double getPrice() {
        return this.price;
    }
}
