package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalInvestingAccountTest {
    private PersonalInvestingAccount testAccount;
    List<Stock> testStockList;
    List<Integer> testStockNumSharesList;
    List<Double> testStockValuesList;
    Stock apple = new Stock("Apple Inc", "$AAPL", 138.38);
    Stock amazon = new Stock("Amazon.com, Inc.", "$AMZN", 106.90);

    @BeforeEach
    public void setUp() {
        this.testAccount = new PersonalInvestingAccount();
        this.testStockList = new ArrayList<>();
        this.testStockNumSharesList = new ArrayList<>();
        this.testStockValuesList = new ArrayList<>();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testAccount.getCashBalance());
        assertEquals(0, testAccount.getAccountValue());
        assertEquals(0, testAccount.getDepositAmount());
        assertEquals(0, testAccount.getStocksPurchased().size());
        assertEquals(0, testAccount.getStocksNumSharesPurchased().size());
        assertEquals(0, testAccount.getStockValues().size());
    }

    @Test
    public void testDepositMoneyOnce() {
        testAccount.depositMoney(500);
        assertEquals(500, testAccount.getCashBalance());
        assertEquals(500, testAccount.getDepositAmount());
    }

    @Test
    public void testDepositMoneyTwice() {
        testAccount.depositMoney(500);
        testAccount.depositMoney(500);
        assertEquals(1000, testAccount.getCashBalance());
        assertEquals(1000, testAccount.getDepositAmount());
    }

    @Test
    public void testPurchaseStockOnce() {
        testAccount.depositMoney(500);
        testAccount.purchaseStock(apple, 2);
        testStockList.add(apple);
        testStockNumSharesList.add(2);
        testStockValuesList.add(276.76);
        assertEquals(223.24, testAccount.getCashBalance());
        assertEquals(500, testAccount.getDepositAmount());
        assertEquals(testStockList.get(0), testAccount.getStocksPurchased().get(0));
        assertEquals(testStockNumSharesList.get(0), testAccount.getStocksNumSharesPurchased().get(0));
        assertEquals(testStockValuesList.get(0), testAccount.getStockValues().get(0));
        assertEquals(1, testAccount.getStocksPurchased().size());
        assertEquals(1, testAccount.getStocksNumSharesPurchased().size());
        assertEquals(1, testAccount.getStockValues().size());
    }

    @Test
    public void testPurchaseStockTwiceWithSameStock() {
        testAccount.depositMoney(500);
        testAccount.purchaseStock(apple, 2);
        testAccount.purchaseStock(apple, 1);
        testStockList.add(apple);
        testStockNumSharesList.add(3);
        testStockValuesList.add(415.14);
        assertEquals(84.86, testAccount.getCashBalance());
        assertEquals(500, testAccount.getDepositAmount());
        assertEquals(testStockList.get(0), testAccount.getStocksPurchased().get(0));
        assertEquals(testStockNumSharesList.get(0), testAccount.getStocksNumSharesPurchased().get(0));
        assertEquals(testStockValuesList.get(0), testAccount.getStockValues().get(0));
        assertEquals(1, testAccount.getStocksPurchased().size());
        assertEquals(1, testAccount.getStocksNumSharesPurchased().size());
        assertEquals(1, testAccount.getStockValues().size());
    }

    @Test
    public void testPurchaseStockTwiceWithDifferentStocks() {
        testAccount.depositMoney(500);
        testAccount.purchaseStock(apple, 2);
        testAccount.purchaseStock(amazon, 1);
        testStockList.add(apple);
        testStockList.add(amazon);
        testStockNumSharesList.add(2);
        testStockNumSharesList.add(1);
        testStockValuesList.add(276.76);
        testStockValuesList.add(106.90);
        assertEquals(116.34, testAccount.getCashBalance());
        assertEquals(500, testAccount.getDepositAmount());
        assertEquals(testStockList.get(0), testAccount.getStocksPurchased().get(0));
        assertEquals(testStockNumSharesList.get(0), testAccount.getStocksNumSharesPurchased().get(0));
        assertEquals(testStockValuesList.get(0), testAccount.getStockValues().get(0));
        assertEquals(testStockList.get(1), testAccount.getStocksPurchased().get(1));
        assertEquals(testStockNumSharesList.get(1), testAccount.getStocksNumSharesPurchased().get(1));
        assertEquals(testStockValuesList.get(1), testAccount.getStockValues().get(1));
        assertEquals(2, testAccount.getStocksPurchased().size());
        assertEquals(2, testAccount.getStocksNumSharesPurchased().size());
        assertEquals(2, testAccount.getStockValues().size());
    }

    @Test
    public void testSellStockWithSomeRemainingSharesAndOnlyOneStockInAccount() {
        testAccount.depositMoney(1000);
        testAccount.purchaseStock(apple, 2);
        testAccount.sellStock(apple, 1);
        assertEquals(861.62, testAccount.getCashBalance());
        assertTrue(testAccount.getStocksPurchased().contains(apple));
        assertEquals(1, testAccount.getStocksNumSharesPurchased()
                .get(testAccount.getStocksPurchased().indexOf(apple)));
    }

    @Test
    public void testSellStockWithNoRemainingSharesAndOnlyOneStockInAccount() {
        testAccount.depositMoney(1000);
        testAccount.purchaseStock(apple, 2);
        testAccount.sellStock(apple, 2);
        assertEquals(1000, testAccount.getCashBalance());
        assertFalse(testAccount.getStocksPurchased().contains(apple));
        assertFalse(testAccount.getStocksNumSharesPurchased().contains(0));
    }

    @Test
    public void testSellStockWithSomeRemainingSharesAndTwoStocksInAccount() {
        testAccount.depositMoney(1000);
        testAccount.purchaseStock(apple, 2);
        testAccount.purchaseStock(amazon, 2);
        testAccount.sellStock(apple, 1);
        testAccount.sellStock(amazon, 1);
        assertEquals(754.72, testAccount.getCashBalance());
        assertEquals(0, testAccount.getStocksPurchased().indexOf(apple));
        assertEquals(1, testAccount.getStocksPurchased().indexOf(amazon));
        assertEquals(1, testAccount.getStocksNumSharesPurchased()
                .get(testAccount.getStocksPurchased().indexOf(apple)));
        assertEquals(1, testAccount.getStocksNumSharesPurchased()
                .get(testAccount.getStocksPurchased().indexOf(amazon)));
    }
    @Test
    public void testSellStockWithNoRemainingSharesAndTwoStocksInAccount() {
        testAccount.depositMoney(1000);
        testAccount.purchaseStock(apple, 2);
        testAccount.purchaseStock(amazon, 2);
        testAccount.sellStock(apple, 2);
        assertEquals(786.20, testAccount.getCashBalance());
        assertFalse(testAccount.getStocksPurchased().contains(apple));
        assertEquals(0, testAccount.getStocksPurchased().indexOf(amazon));
        assertEquals(2, testAccount.getStocksNumSharesPurchased().get(0));
}

}
