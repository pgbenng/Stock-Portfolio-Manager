package model;

import java.util.ArrayList;
import java.util.List;

public class StockWatchlist {
    private List<Stock> watchlist;

    // EFFECTS: instantiates an empty stock watchlist
    public StockWatchlist() {
        this.watchlist = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds inputted stock into watchlist if not in watchlist
    public void addStockToWatchlist(Stock s) {
        if (!watchlist.contains(s)) {
            this.watchlist.add(s);
        }
    }

    // getters
    public List<Stock> getWatchlist() {
        return this.watchlist;
    }
}
