package ui;

import model.*;
import persistence.*;
import ui.tabs.SaveLoadTab;
import ui.tabs.StatsTab;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StockPortfolioGUI extends JFrame {
    // Represents the stock portfolio application's main page.

    // Citation: Some code is modeled after Oracle's Java Swing demo files, AlarmSystem and SmartHome.
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

    /*
    TODO:
    - Button to load data
    - Visual component:
    - Code comments
     */

    private PersonalInvestingAccount account;
    private JTabbedPane sidebar;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JPanel statsTab;
    private JPanel saveLoadTab;

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

    public PersonalInvestingAccount getAccount() {
        return this.account;
    }

    public void setAccount(PersonalInvestingAccount account) {
        this.account = account;
    }

    // EFFECTS: adds menu bar
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenuItem deposit = new JMenuItem(new DepositAction(account));
        menuBar.add(deposit);

        JMenuItem purchaseStock = new JMenuItem(new PurchaseStockAction(account));
        menuBar.add(purchaseStock);

        setJMenuBar(menuBar);
    }

    private class DepositAction extends AbstractAction {
        private PersonalInvestingAccount account;

        DepositAction(PersonalInvestingAccount account) {
            super("Deposit");
            this.account = account;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String depositAmount = JOptionPane.showInputDialog(null,
                    "Enter deposit amount below:", "Deposit Menu", JOptionPane.QUESTION_MESSAGE);
            account.depositMoney(Double.parseDouble(depositAmount));
        }
    }

    private class PurchaseStockAction extends AbstractAction {
        private PersonalInvestingAccount account;

        PurchaseStockAction(PersonalInvestingAccount account) {
            super("Purchase Stock");
            this.account = account;
        }

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

    private void addTabs() {
        statsTab = new StatsTab(this, account);
        saveLoadTab = new SaveLoadTab(this, account);
        sidebar.add(statsTab, 0);
        sidebar.setTitleAt(0, "Portfolio Statistics");
        sidebar.add(saveLoadTab, 1);
        sidebar.setTitleAt(1, "Save/Load");
    }

    public static void main(String[] args) {
        new StockPortfolioGUI();
    }
}
