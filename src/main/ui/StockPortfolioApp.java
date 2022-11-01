package ui;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

// Some code from TellerApp and JsonSerializationDemo was referenced during the implementation of this file's code
// Represents the investment account application
public class StockPortfolioApp {
    private static final String JSON_STORE = "./data/account.json";
    private Scanner scan;
    private PersonalInvestingAccount investingAccount;
    private JsonReader reader;
    private JsonWriter writer;

    // EFFECTS: runs the StockPortfolio console application
    public StockPortfolioApp() {
        writer = new JsonWriter(JSON_STORE);
        reader = new JsonReader(JSON_STORE);
        runStockPortfolioApp();
    }

    // EFFECTS: keeps the application up until user quits with input
    private void runStockPortfolioApp() {
        boolean running = true;
        String command = null;
        this.scan = new Scanner(System.in);
        this.investingAccount = new PersonalInvestingAccount();
        displayMenu();
        while (running) {
            command = this.scan.nextLine().toLowerCase();
            if (command.equals("q")) {
                running = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\n Goodbye!");
    }

    // EFFECTS: displays option menu to user
    private void displayMenu() {
        System.out.println("Welcome to my Stock Portfolio App! We have created an investment account for you.");
        System.out.println("Select from the following commands:");
        System.out.println("- \"depo\" to deposit money into your account.");
        System.out.println("- \"buy\" to buy a stock.");
        System.out.println("- \"sell\" to sell a stock.");
        System.out.println("- \"stats\" to display your investment account statistics.");
        System.out.println("- \"save\" to save your account to file.");
        System.out.println("- \"load\" to load an account from file.");
        System.out.println("\"q\" to quit.");
    }

    // MODIFIES: this
    // EFFECTS: processes commands from user
    private void processCommand(String command) {
        if (command.equals("depo")) {
            processDepositCommand();
        } else if (command.equals("buy")) {
            processBuyStockCommand();
        } else if (command.equals("sell")) {
            processSellStockCommand();
        } else if (command.equals("stats")) {
            processDisplayInvestingAccount();
        } else if (command.equals("save")) {
            processSaveInvestingAccount();
        } else if (command.equals("load")) {
            processLoadInvestingAccount();
        } else if (command.equals("")) {
            System.out.print("");
        } else {
            System.out.println("Invalid command! Try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: invokes depositMoney method using parameter given by user input
    private void processDepositCommand() {
        System.out.println("Please enter the amount you would like deposited.");
        double depositAmount = scan.nextDouble();
        this.investingAccount.depositMoney(depositAmount);
        System.out.println("Deposit successful.");
        System.out.println("Your account balance is now $" + this.investingAccount.getCashBalance());
    }

    // MODIFIES: this
    // EFFECTS: invokes purchaseStock method, where user inputs all stock info and purchase details to pass along
    private void processBuyStockCommand() {
        System.out.println("Please enter the stock details as follows: ");
        System.out.print("Stock name: ");
        String stockName = scan.nextLine();
        System.out.print("Stock ticker: ");
        String stockTicker = scan.nextLine();
        System.out.print("Stock current price: ");
        double stockPrice = scan.nextDouble();
        Stock inputStock = new Stock(stockName, stockTicker, stockPrice);
        System.out.println("Number of shares you want to purchase: ");
        int numShares = scan.nextInt();
        this.investingAccount.purchaseStock(inputStock, numShares);
        System.out.println("Stock purchase successful.");
        System.out.println("Your account balance is now $" + this.investingAccount.getCashBalance() + ".");
    }

    public void processSellStockCommand() {
        System.out.println("Please enter the stock details as follows: ");
        System.out.print("Stock name: ");
        String stockName = scan.nextLine();
        System.out.print("Stock ticker: ");
        String stockTicker = scan.nextLine();
        System.out.print("Stock current price: ");
        double stockPrice = scan.nextDouble();
        Stock inputStock = new Stock(stockName, stockTicker, stockPrice);
        System.out.println("Number of shares you want to sell: ");
        int numShares = scan.nextInt();
        this.investingAccount.sellStock(inputStock, numShares);
        System.out.println("Stock sale successful.");
        System.out.println("Your account balance is now $" + this.investingAccount.getCashBalance() + ".");
    }

    // EFFECTS: displays the user's existing investing account general statistics
    private void processDisplayInvestingAccount() {
        System.out.println("You currently own " + this.investingAccount.getStocksPurchased().size()
                + " different stocks!");
        System.out.println("Your account balance is $" + this.investingAccount.getCashBalance() + ".");
        System.out.println("You have deposited a total amount of $" + this.investingAccount.getDepositAmount() + "!");
        if (this.investingAccount.getStocksPurchased().size() > 0) {
            displayListOfStocks();
            lookStock();
        }
    }

    // EFFECTS: displays the user's list of stocks in their investing account
    private void displayListOfStocks() {
        for (int i = 0; i < this.investingAccount.getStocksPurchased().size(); i++) {
            System.out.println(i + ": " + this.investingAccount.getStocksPurchased().get(i).getTicker());
        }
    }

    // EFFECTS: grants the user the option to look at detailed statistics of stocks in their account
    private void lookStock() {
        System.out.println("Type each stock's associated index for more detailed statistics! Type 1000 to stop.");
        boolean running = true;
        while (running) {
            int index = scan.nextInt();
            if (index >= 0 && index < investingAccount.getStocksPurchased().size()) {
                System.out.println("Stock name: " + this.investingAccount.getStocksPurchased().get(index).getName());
                System.out.println("Stock ticker: "
                        + this.investingAccount.getStocksPurchased().get(index).getTicker());
                System.out.println("Stock price: " + this.investingAccount.getStocksPurchased().get(index).getPrice());
                System.out.println("Number of shares of stock purchased: "
                        + this.investingAccount.getStocksNumSharesPurchased().get(index));
                // bugged System.out.println("Stock value: "
                        //+ this.investingAccount.getStockValues().get(index));
            } else if (index < 0 && index >= investingAccount.getStocksPurchased().size()
                    || index == (int)index) {
                running = false;
            }
        }
    }

    // EFFECTS: saves investing account to file
    private void processSaveInvestingAccount() {
        try {
            writer.open();
            writer.write(investingAccount);
            writer.close();
            System.out.println("Saved account successfully to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + JSON_STORE);
        }
    }

    // EFFECTS: loads investing account to file
    private void processLoadInvestingAccount() {
        try {
            investingAccount = reader.read();
            System.out.println("Loaded account from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
