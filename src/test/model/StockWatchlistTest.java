package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StockWatchlistTest {
    private StockWatchlist testWatchlist;
    private List<Stock> accurateList;
    Stock apple = new Stock("Apple Inc", "$AAPL", 138.38);
    Stock amazon = new Stock("Amazon.com, Inc.", "$AMZN", 106.90);

    @BeforeEach
    public void setUp() {
        this.testWatchlist = new StockWatchlist();
        this.accurateList = new ArrayList<>();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testWatchlist.getWatchlist().size());
    }

    @Test
    public void testAddStockToWatchlistWithOneStock() {
        this.testWatchlist.addStockToWatchlist(apple);
        this.accurateList.add(apple);
        assertEquals(this.accurateList.get(0), this.testWatchlist.getWatchlist().get(0));
        assertEquals(1, this.testWatchlist.getWatchlist().size());
    }

    @Test
    public void testAddStockToWatchlistWithOneStockButTwice() {
        this.testWatchlist.addStockToWatchlist(apple);
        this.testWatchlist.addStockToWatchlist(apple);
        this.accurateList.add(apple);
        assertEquals(this.accurateList.get(0), this.testWatchlist.getWatchlist().get(0));
        assertEquals(1, this.testWatchlist.getWatchlist().size());
    }

    @Test
    public void testAddStockToWatchlistWithTwoDifferentStocks() {
        this.testWatchlist.addStockToWatchlist(apple);
        this.testWatchlist.addStockToWatchlist(amazon);
        this.accurateList.add(apple);
        this.accurateList.add(amazon);
        assertEquals(this.accurateList.get(0), this.testWatchlist.getWatchlist().get(0));
        assertEquals(this.accurateList.get(1), this.testWatchlist.getWatchlist().get(1));
        assertEquals(2, this.testWatchlist.getWatchlist().size());
    }
}
