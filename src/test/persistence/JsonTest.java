package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkStock(String name, String ticker, double price, Stock s) {
        assertEquals(name, s.getName());
        assertEquals(ticker, s.getTicker());
        assertEquals(price, s.getPrice());
    }
}
