package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockTest {
    private Stock testStock;

    @BeforeEach
    public void setUp() {
        this.testStock = new Stock("University of British Columbia", "$UBC", 12000);
    }

    @Test
    public void testConstructor() {
        assertEquals("University of British Columbia", testStock.getName());
        assertEquals("$UBC", testStock.getTicker());
        assertEquals(12000, testStock.getPrice());
    }
}
