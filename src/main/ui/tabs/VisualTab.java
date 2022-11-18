package ui.tabs;

import model.PersonalInvestingAccount;
import ui.StockPortfolioGUI;

import javax.swing.*;
import java.awt.*;

// Represents the visual tab on the sidebar
public class VisualTab extends Tab {
    private JLabel visual;
    // Image source: https://caltech-prod.s3.amazonaws.com/main/images/CollinCamerer-ShortSelling-0.2e16d0ba.fill-1600x810-c100.jpg
    private static final String PROJECT_VISUAL = "./data/visual.jpg";

    //EFFECTS: constructs a visual tab for console, places visual in frame
    public VisualTab(StockPortfolioGUI controller, PersonalInvestingAccount account) {
        super(controller, account);
        setLayout(new FlowLayout());
        placeVisual();
    }

    // MODIFIES: this
    // helper to put the visual into VisualTab's frame
    private void placeVisual() {
        visual = new JLabel(new ImageIcon(PROJECT_VISUAL), JLabel.CENTER);
        this.add(visual);
    }

}
