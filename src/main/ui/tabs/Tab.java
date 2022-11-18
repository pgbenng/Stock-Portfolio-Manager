package ui.tabs;

import model.PersonalInvestingAccount;
import ui.StockPortfolioGUI;

import javax.swing.*;
import java.awt.*;

// abstract class that represents all sidebar tabs in frame
public abstract class Tab extends JPanel {
    protected PersonalInvestingAccount account;
    protected StockPortfolioGUI controller;

    //REQUIRES: StockPortfolioGUI controller that holds this tab
    public Tab(StockPortfolioGUI controller, PersonalInvestingAccount account) {
        this.controller = controller;
        this.account = account;
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

}
