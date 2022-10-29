package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// Citation: All following code is modeled after JsonSerializationDemo.
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PersonalInvestingAccount account = reader.read();
            fail("IOException expected.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            PersonalInvestingAccount account = reader.read();
            assertEquals(0, account.getCashBalance());
            assertEquals(0, account.getAccountValue());
            assertEquals(0, account.getDepositAmount());
            assertEquals(0, account.getStocksPurchased().size());
            assertEquals(0, account.getStocksNumSharesPurchased().size());
            assertEquals(0, account.getStockValues().size());
        } catch (IOException e){
            fail("Couldn't read from file.");
        }
    }

    @Test
    public void testReaderGeneralAccount() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            PersonalInvestingAccount account = reader.read();
            assertEquals(1000, account.getCashBalance());
            assertEquals(1000, account.getAccountValue());
            assertEquals(1000, account.getDepositAmount());
            assertEquals(2, account.getStocksPurchased().size());
            checkStock("Apple", "$AAPL", 10, account.getStocksPurchased().get(0));
            checkStock("Amazon", "$AMZN", 5, account.getStocksPurchased().get(1));
            assertEquals(2, account.getStocksNumSharesPurchased().size());
            assertEquals(2, account.getStocksNumSharesPurchased().get(0));
            assertEquals(1, account.getStocksNumSharesPurchased().get(1));
            assertEquals(2, account.getStockValues().size());
            assertEquals(10, account.getStockValues().get(0));
            assertEquals(9, account.getStockValues().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file.");
        }
    }
}
