package ui.tabs;

import model.PersonalInvestingAccount;
import model.Stock;
import persistence.*;
import ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents the stats tab on the sidebar
public class StatsTab extends Tab {
    private JLabel greeting;
    private JLabel statCashBalance;
    private JLabel statNumStocksOwned;
    private static JList statsStocks;

    //EFFECTS: constructs a portfolio statistics tab for console
    public StatsTab(StockPortfolioGUI controller, PersonalInvestingAccount account) {
        super(controller, account);
        setLayout(new GridLayout(10, 1));

        placeGreeting();
        placeStats();
        placeUpdateButtons();
        placeStocksAdded();
    }

    // MODIFIES: this
    // helper to put the greeting into StatsTab's frame
    private void placeGreeting() {
        greeting = new JLabel("Here are your portfolio statistics!", JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 5);
        this.add(greeting);
    }

    // MODIFIES: this
    // helper to put account balance and stock info into frame
    private void placeStats() {
        statCashBalance = new JLabel("Your account balance is $" + this.account.getCashBalance()
                + ".", JLabel.CENTER);
        statCashBalance.setSize(WIDTH, HEIGHT / 10);
        this.add(statCashBalance);
        statNumStocksOwned = new JLabel("You currently own " + this.account.getStocksPurchased().size()
                + " different stocks!", JLabel.CENTER);
        statNumStocksOwned.setSize(WIDTH, HEIGHT / 10);
        this.add(statNumStocksOwned);
        statNumStocksOwned.setSize(WIDTH, HEIGHT / 10);
    }

    // MODIFIES: this
    // helper to put account balance and stock info update buttons into frame
    private void placeUpdateButtons() {
        JButton b1 = new JButton("Update Balance");
        JButton b2 = new JButton("Update Stock Data");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setSize(WIDTH, HEIGHT / 10);

        b1.addActionListener(e -> {
            statCashBalance.setText("Your account balance is $" + this.account.getCashBalance()
                    + ".");
        });

        b2.addActionListener(e -> {
            statNumStocksOwned.setText("You currently own " + this.account.getStocksPurchased().size()
                    + " different stocks!");
            placeStocksAdded();
        });

        this.add(buttonRow);
    }

    // MODIFIES: this
    // EFFECTS: displays JList of account's owned stocks
    private void placeStocksAdded() {
        List<String> statsStocksList = new ArrayList<>();
        if (statsStocks != null) {
            this.remove(statsStocks);
        }
        for (int i = 0; i < account.getStocksPurchased().size(); i++) {
            statsStocksList.add("- " + account.getStocksPurchased().get(i).getName() + ": "
                    + account.getStocksNumSharesPurchased().get(i) + " shares, $"
                    + account.getStocksPurchased().get(i).getPrice() + " average price.");
        }
        statsStocks = new JList(statsStocksList.toArray());
        this.add(statsStocks);
    }

}
