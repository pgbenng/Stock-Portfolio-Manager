package ui;

import model.*;
import persistence.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StockPortfolioGUI extends JFrame {
    // Represents the stock portfolio application's main page.

    // Citation: Some code is modeled after Oracle's Java Swing demo files and AlarmSystem.
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

    /*
    Starts with empty account, needs depo, account stats, each stock in acc stats, buy, sell, save, load.
    TODO:
    - Event that adds multiple stocks to account
    - Event that lets user look at specifics of each stock
    - Button to save data
    - Button to load data
    - Visual component:
     */

    private PersonalInvestingAccount account;
    private JDesktopPane desktop;
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    public StockPortfolioGUI() {
        account = new PersonalInvestingAccount();

        desktop = new JDesktopPane();
        setContentPane(desktop);

        setTitle("StockPortfolio");
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        addMenu();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    // EFFECTS: adds menu bar
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenuItem deposit = new JMenuItem(new DepositAction(account));
        menuBar.add(deposit);

        JMenuItem purchaseStock = new JMenuItem(new PurchaseStockAction(account));
        menuBar.add(purchaseStock);

        JMenu stats = new JMenu("Portfolio Statistics");
        menuBar.add(stats);

        JMenu save = new JMenu("Save");
        menuBar.add(save);

        JMenu load = new JMenu("Load");
        menuBar.add(load);

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
            account.depositMoney(Integer.parseInt(depositAmount));

            // for use during debugging
            System.out.println(account.getCashBalance());
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
            Stock stock = new Stock(stockName, stockTicker, Double.parseDouble(stockPrice));
            account.purchaseStock(stock, Integer.parseInt(shareAmount));

            // for use during debugging
            for (Stock s : account.getStocksPurchased()) {
                System.out.println(s.getName());
            }
            System.out.println(account.getStocksNumSharesPurchased());
            System.out.println(account.getCashBalance());
        }
    }

    public static void main(String[] args) {
        new StockPortfolioGUI();
    }
}
