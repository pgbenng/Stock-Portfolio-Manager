package persistence;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import model.*;

import static org.junit.jupiter.api.Assertions.*;

// Citation: Some code is modeled after JsonSerializationDemo.
public class JsonWriterTest extends JsonTest {
    @Test
    public void testWriterInvalidFile() {
        try {
            PersonalInvestingAccount account = new PersonalInvestingAccount();
            JsonWriter writer = new JsonWriter("./data/lol\0bad:fileName.json");
            writer.open();
            fail("Exception was expected.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyAccount() {
        try {
            PersonalInvestingAccount account = new PersonalInvestingAccount();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(account);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            account = reader.read();
            assertEquals(0, account.getCashBalance());
            assertEquals(0, account.getAccountValue());
            assertEquals(0, account.getDepositAmount());
            assertEquals(0, account.getStocksPurchased().size());
            assertEquals(0, account.getStocksNumSharesPurchased().size());
            assertEquals(0, account.getStockValues().size());
        } catch (IOException e) {
            fail("Exception was not expected here.");
        }
    }

    @Test
    public void testWriterGeneralAccount() {
        try {
            PersonalInvestingAccount account = testAccount();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(account);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            account = reader.read();
            assertEquals(5000, account.getCashBalance());
            assertEquals(1000, account.getAccountValue());
            assertEquals(3000, account.getDepositAmount());
            assertEquals(2, account.getStocksPurchased().size());
            checkStock("a", "$a", 2, account.getStocksPurchased().get(0));
            checkStock("b", "$b", 1, account.getStocksPurchased().get(1));
            assertEquals(2, account.getStocksNumSharesPurchased().size());
            assertEquals(2, account.getStocksNumSharesPurchased().get(0));
            assertEquals(1, account.getStocksNumSharesPurchased().get(1));
            assertEquals(2, account.getStockValues().size());
            assertEquals(100.0, account.getStockValues().get(0));
            assertEquals(200.0, account.getStockValues().get(1));
        } catch (IOException e) {
            fail("Exception was not expected here.");
        }
    }

    // helper to create a general account for testing
    private PersonalInvestingAccount testAccount() {
        PersonalInvestingAccount account = new PersonalInvestingAccount();
        account.setCashBalance(5000);
        account.setAccountValue(1000);
        account.setDepositAmount(3000);
        account.addStocksPurchased(new Stock("a", "$a", 2));
        account.addStocksPurchased(new Stock("b", "$b", 1));
        account.addStocksNumSharesPurchased(2);
        account.addStocksNumSharesPurchased(1);
        account.addStockValues(100.0);
        account.addStockValues(200.0);
        return account;
    }
}
