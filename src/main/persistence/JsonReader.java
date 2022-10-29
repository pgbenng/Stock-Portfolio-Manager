package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import java.io.IOException;

// Citation: All following code is modeled after JsonSerializationDemo.
// Represents a reader that reads an investing account from JSON data stored in file.
public class JsonReader {
    private String source;

    // EFFECTS: constructs JSON file reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads an investing account from file and returns it;
    // throws an IOException if error occurs from reading the file
    public PersonalInvestingAccount read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePersonalInvestingAccount(jsonObject);
    }

    // EFFECTS: reads the source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses the investing account from JSON object and return it
    private PersonalInvestingAccount parsePersonalInvestingAccount(JSONObject jsonObject) {
        double cashBalance = jsonObject.getDouble("cashBalance");
        double accountValue = jsonObject.getDouble("accountValue");
        double depositAmount = jsonObject.getDouble("depositAmount");
        PersonalInvestingAccount account = new PersonalInvestingAccount();
        account.setCashBalance(cashBalance);
        account.setAccountValue(accountValue);
        account.setDepositAmount(depositAmount);
        addStocksPurchased(account, jsonObject);
        addNumSharesPurchased(account, jsonObject);
        addStockValues(account, jsonObject);
        return account;
    }

    // MODIFIES: account
    // EFFECTS: parses purchased stocks from JSON object and adds them to account
    private void addStocksPurchased(PersonalInvestingAccount account, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("stocksPurchased");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            addStock(account, nextStock);
        }
    }

    // MODIFIES: account
    // EFFECTS: parses Stock from JSON object and adds it to account
    private void addStock(PersonalInvestingAccount account, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String ticker = jsonObject.getString("ticker");
        double price = jsonObject.getDouble("price");
        Stock stock = new Stock(name, ticker, price);
        account.addStocksPurchased(stock);
    }

    // MODIFIES: account
    // EFFECTS: parses number of shares purchased of each stock from JSON object and adds them to account
    private void addNumSharesPurchased(PersonalInvestingAccount account, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("stocksNumSharesPurchased");
        for (int i = 0; i < jsonArray.length(); i++) {
            account.addStocksNumSharesPurchased(jsonArray.getInt(i));
        }
    }

    // MODIFIES: account
    // EFFECTS: parses each stock value from JSON object and adds them to account
    private void addStockValues(PersonalInvestingAccount account, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("stockValues");
        for (int i = 0; i < jsonArray.length(); i++) {
            account.addStockValues(jsonArray.getDouble(i));
        }
    }


}
