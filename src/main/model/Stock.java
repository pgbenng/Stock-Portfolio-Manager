package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class Stock implements Writable {
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

    // setters
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("ticker", ticker);
        json.put("price", price);
        return json;
    }
}
