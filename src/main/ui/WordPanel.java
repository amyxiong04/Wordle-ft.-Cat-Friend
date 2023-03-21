package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WordPanel extends JPanel {

    JLabel[] wordColumns = new JLabel[5];

    public WordPanel() {
        this.setLayout(new GridLayout(1, 5));
        Border blackBorder = BorderFactory.createLineBorder(Color.GRAY);
        for (int i = 0; i < 5; i++) {
            wordColumns[i] = new JLabel();
            wordColumns[i].setHorizontalAlignment(JLabel.CENTER);
            wordColumns[i].setOpaque(true);
            wordColumns[i].setBorder(blackBorder);
            this.add(wordColumns[i]);
        }
    }

    public void setPanelText(String charValue, int position, Color color) {
        this.wordColumns[position].setText(charValue);
        this.wordColumns[position].setBackground(color);
    }
}
