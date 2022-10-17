package ui;

import model.*;

import java.util.Locale;
import java.util.Scanner;

// Some code from TellerApp was referenced during the implementation of this file's code
public class StockPortfolioApp {
    private Scanner scan;
    private PersonalInvestingAccount investingAccount;
    private StockWatchlist watchlist;

    // EFFECTS: runs the StockPortfolio console application
    public StockPortfolioApp() {
        runStockPortfolioApp();
    }

    // EFFECTS: keeps the application up until user quits with input
    private void runStockPortfolioApp() {
        boolean running = true;
        String command = null;
        this.scan = new Scanner(System.in);
        this.investingAccount = new PersonalInvestingAccount();
        this.watchlist = new StockWatchlist();
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
        System.out.println("- \"d\" to deposit money into your account.");
        System.out.println("- \"adds\" to purchase a stock and add it to your account.");
        System.out.println("- \"addw\" to add a stock to your watchlist.");
        System.out.println("- \"da\" to display your investment account statistics.");
        System.out.println("- \"dw\" to display your stock watchlist statistics.");
        System.out.println("\"q\" to quit.");
    }

    // MODIFIES: this
    // EFFECTS: processes commands from user
    private void processCommand(String command) {
        if (command.equals("d")) {
            processDepositCommand();
        } else if (command.equals("adds")) {
            processPurchaseStockCommand();
        } else if (command.equals("addw")) {
            processAddStockToWatchlist();
        } else if (command.equals("da")) {
            processDisplayInvestingAccount();
        } else if (command.equals("dw")) {
            processDisplayStockWatchlist();
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
    }

    // MODIFIES: this
    // EFFECTS: invokes purchaseStock method, where user inputs all stock info and purchase details to pass along
    private void processPurchaseStockCommand() {
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
    }

    // MODIFIES: this
    // EFFECTS: invokes addStockToWatchlist method, where user inputs all stock info and purchase details to pass along
    private void processAddStockToWatchlist() {
        System.out.println("Please enter the stock details as follows: ");
        System.out.print("Stock name: ");
        String stockName = scan.nextLine();
        System.out.print("Stock ticker: ");
        String stockTicker = scan.nextLine();
        System.out.print("Stock current price: ");
        double stockPrice = scan.nextDouble();
        Stock inputStock = new Stock(stockName, stockTicker, stockPrice);
        this.watchlist.addStockToWatchlist(inputStock);
    }

    // EFFECTS: displays the user's existing investing account general statistics
    private void processDisplayInvestingAccount() {
        System.out.println("You currently own " + this.investingAccount.getStocksPurchased().size()
                + " different stocks!");
        System.out.println("You have deposited a total amount of $" + this.investingAccount.getDepositAmount() + "!");
        if (this.investingAccount.getStocksPurchased().size() > 0) {
            displayListOfStocks();
            lookStock();
        }
    }

    // EFFECTS: displays the user's list of stocks in their investing account
    private void displayListOfStocks() {
        for (int i = 0; i < investingAccount.getStocksPurchased().size(); i++) {
            System.out.println(i + ": " + investingAccount.getStocksPurchased().get(i).getTicker());
        }
    }

    // EFFECTS: grants the user the option to look at detailed statistics of stocks in their account
    private void lookStock() {
        System.out.println("Type each stock's associated index for more detailed statistics! Type \"quit\" to stop.");
        int index = scan.nextInt();
        if (index >= 0 && index < investingAccount.getStocksPurchased().size()) {
            System.out.println("Stock name: " + this.investingAccount.getStocksPurchased().get(index).getName());
            System.out.println("Stock ticker: "
                    + this.investingAccount.getStocksPurchased().get(index).getTicker());
            System.out.println("Stock price: " + this.investingAccount.getStocksPurchased().get(index).getPrice());
            System.out.println("Number of shares of stock purchased: "
                    + this.investingAccount.getStocksNumSharesPurchased().get(index));
            System.out.println("Stock value: "
                    + this.investingAccount.getStockValues().get(index));
        }
    }

    // EFFECTS: displays the user's stock watchlist
    private void processDisplayStockWatchlist() {
        System.out.println("You currently have " + this.watchlist.getWatchlist().size()
                + " different stocks in your watchlist!");
        for (int i = 0; i < watchlist.getWatchlist().size(); i++) {
            System.out.println(i + ": " + watchlist.getWatchlist().get(i).getTicker());
        }
    }
}
