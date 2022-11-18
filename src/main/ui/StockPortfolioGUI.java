package ui;

import model.*;
import persistence.*;
import ui.tabs.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents the Graphical User Interface for the StockPortfolio app
public class StockPortfolioGUI extends JFrame {
    // Represents the stock portfolio application's main page.
    // Citation: Some code is modeled after Oracle's Java Swing demo files, AlarmSystem and SmartHome.
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

    private PersonalInvestingAccount account;
    private JTabbedPane sidebar;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JPanel statsTab;
    private JPanel saveLoadTab;
    private JPanel visualTab;

    // EFFECTS: Constructs GUI, adding menu bar and sidebar and specifying frame size, title, layout and close behaviour
    public StockPortfolioGUI() {
        setTitle("StockPortfolio");
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        account = new PersonalInvestingAccount();
        addMenu();
        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.RIGHT);
        addTabs();
        add(sidebar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // account getter
    public PersonalInvestingAccount getAccount() {
        return this.account;
    }

    // account setter
    public void setAccount(PersonalInvestingAccount account) {
        this.account = account;
    }

    // EFFECTS: adds menu bar to frame
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenuItem deposit = new JMenuItem(new DepositAction(account));
        menuBar.add(deposit);

        JMenuItem purchaseStock = new JMenuItem(new PurchaseStockAction(account));
        menuBar.add(purchaseStock);

        JMenuItem sellStock = new JMenuItem(new SellStockAction(account));
        menuBar.add(sellStock);

        setJMenuBar(menuBar);
    }

    // helper class that represents deposit menu button
    private class DepositAction extends AbstractAction {
        private PersonalInvestingAccount account;

        // EFFECTS: Sets menu item name, passes along GUI's account
        DepositAction(PersonalInvestingAccount account) {
            super("Deposit");
            this.account = account;
        }

        // EFFECTS: specifies action of button: performs deposit function
        @Override
        public void actionPerformed(ActionEvent event) {
            String depositAmount = JOptionPane.showInputDialog(null,
                    "Enter deposit amount below:", "Deposit Menu", JOptionPane.QUESTION_MESSAGE);
            account.depositMoney(Double.parseDouble(depositAmount));
        }
    }

    // helper class that represents purchase stock menu button
    private class PurchaseStockAction extends AbstractAction {
        private PersonalInvestingAccount account;

        // EFFECTS: Sets menu item name, passes along GUI's account
        PurchaseStockAction(PersonalInvestingAccount account) {
            super("Purchase Stock");
            this.account = account;
        }

        // EFFECTS: specifies action of button: performs purchaseStock function, doesn't do anything if one of user's
        //          inputs are null.
        @Override
        public void actionPerformed(ActionEvent event) {
            String stockName = JOptionPane.showInputDialog(null,
                    "Enter stock name below:", "Add Stock Menu", JOptionPane.QUESTION_MESSAGE);
            String stockTicker = JOptionPane.showInputDialog(null,
                    "Enter stock ticker below:", "Add Stock Menu", JOptionPane.QUESTION_MESSAGE);
            String stockPrice = JOptionPane.showInputDialog(null,
                    "Enter stock price below:", "Add Stock Menu", JOptionPane.QUESTION_MESSAGE);
            String shareAmount = JOptionPane.showInputDialog(null,
                    "Enter number of shares below:", "Add Stock Menu", JOptionPane.QUESTION_MESSAGE);
            if ((stockName != null) && (stockTicker != null) && (stockPrice != null) && (shareAmount != null)) {
                Stock stock = new Stock(stockName, stockTicker, Double.parseDouble(stockPrice));
                account.purchaseStock(stock, Integer.parseInt(shareAmount));
            }
        }
    }

    // helper class that represents sell stock menu button
    private class SellStockAction extends AbstractAction {
        private PersonalInvestingAccount account;

        // EFFECTS: Sets menu item name, passes along GUI's account
        SellStockAction(PersonalInvestingAccount account) {
            super("Sell Stock");
            this.account = account;
        }

        // EFFECTS: specifies action of button: performs sellStock function, doesn't do anything if one of user's
        //          inputs are null.
        @Override
        public void actionPerformed(ActionEvent event) {
            String stockName = JOptionPane.showInputDialog(null,
                    "Enter stock name below:", "Sell Stock Menu", JOptionPane.QUESTION_MESSAGE);
            String stockTicker = JOptionPane.showInputDialog(null,
                    "Enter stock ticker below:", "Sell Stock Menu", JOptionPane.QUESTION_MESSAGE);
            String stockPrice = JOptionPane.showInputDialog(null,
                    "Enter stock price below:", "Sell Stock Menu", JOptionPane.QUESTION_MESSAGE);
            String shareAmount = JOptionPane.showInputDialog(null,
                    "Enter number of shares below:", "Sell Stock Menu", JOptionPane.QUESTION_MESSAGE);
            if ((stockName != null) && (stockTicker != null) && (stockPrice != null) && (shareAmount != null)) {
                Stock stock = new Stock(stockName, stockTicker, Double.parseDouble(stockPrice));
                account.sellStock(stock, Integer.parseInt(shareAmount));
            }
        }
    }

    // EFFECTS: adds sidebar to frame
    private void addTabs() {
        statsTab = new StatsTab(this, account);
        saveLoadTab = new SaveLoadTab(this, account);
        visualTab = new VisualTab(this, account);
        sidebar.add(statsTab, 0);
        sidebar.setTitleAt(0, "Portfolio Statistics");
        sidebar.add(saveLoadTab, 1);
        sidebar.setTitleAt(1, "Save/Load");
        sidebar.add(visualTab, 2);
        sidebar.setTitleAt(2, "Visual");
    }

    // EFFECTS: main method that runs GUI
    public static void main(String[] args) {
        new StockPortfolioGUI();
    }
}
