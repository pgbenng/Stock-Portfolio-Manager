package ui.tabs;

import model.PersonalInvestingAccount;
import persistence.*;
import ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SaveLoadTab extends Tab {
    private JLabel greeting;
    private JsonReader reader;
    private JsonWriter writer;
    private static final String JSON_STORE = "./data/account.json";

    //EFFECTS: constructs a save/load functionality tab for console
    public SaveLoadTab(StockPortfolioGUI controller, PersonalInvestingAccount account) {
        super(controller, account);
        setLayout(new GridLayout(5, 1));
        placeGreeting();
        placeSaveLoadButtons();
        writer = new JsonWriter(JSON_STORE);
        reader = new JsonReader(JSON_STORE);
    }

    private void placeGreeting() {
        greeting = new JLabel("You can choose to save your current portfolio or load another from file.",
                JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 5);
        this.add(greeting);
    }

    private void placeSaveLoadButtons() {
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JPanel buttonRow = formatButtonRow(saveButton);
        buttonRow.add(loadButton);
        buttonRow.setSize(WIDTH, HEIGHT / 5);

        saveButton.addActionListener(e -> {
            saveAction();
        });

        loadButton.addActionListener(e -> {
            //loadAction();
        });
        this.add(buttonRow);
    }

    private void saveAction() {
        try {
            writer.open();
            writer.write(controller.getAccount());
            writer.close();
            System.out.println("Saved account successfully to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + JSON_STORE);
        }
    }

    /*
    private void loadAction() {
        try {
            reader.read();
            System.out.println("Loaded account from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        //debug
        System.out.println("Balance: " + this.account.getCashBalance());
    }

     */

    public PersonalInvestingAccount getLoadedAccount() {
        return this.account;
    }
}
