package ui;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {

    private JTextField userInput;
    private JButton makeGuessButton;

    public UserPanel() {
        this.setLayout(new GridLayout(1, 2));
        userInput = new JTextField();
        userInput.setColumns(30);
        this.add(userInput);
        makeGuessButton = new JButton("Make Guess");
        makeGuessButton.setBounds(50, 100, 100, 30);
        this.add(makeGuessButton);
    }

    public JTextField getUserInput() {
        return userInput;
    }

    public JButton getMakeGuessButton() {
        return makeGuessButton;
    }
}
