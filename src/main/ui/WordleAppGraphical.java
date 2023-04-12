package ui;

import model.Event;
import model.EventLog;
import model.Guess;
import model.Log;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// represents the graphical interface of the Wordle application
public class WordleAppGraphical extends JFrame implements ActionListener, WindowListener {
    private static final String JSON_STORE = "./data/log.json";
    private int tries;
    private static String answer;
    private Guess newGuess;
    private Log log;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static JPanel panel;
    private static JFrame frame;
    private static JLabel title;
    private static JTextField userGuess;
    private static JLabel[] labels;
    private static JLabel cat;


    // EFFECTS: initializes Wordle game
    public WordleAppGraphical() {
        this.tries = 0;                                  // unsolved state
        this.answer = "";                                // initial target word
        this.newGuess = new Guess("", answer); // initial guess
        this.log = new Log();                            // instantiates new guess log
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: assesses whether current guess is a winning guess;
    //          returns true if winning guess,
    //          otherwise, returns false
    public Boolean wonGame() {
        List<String> code = newGuess.getColourCode();
        int greenCount = 0;
        for (String s : code) {
            if (s.equals("G")) {
                greenCount++;
            }
        }
        return greenCount == 5;
    }

    // ATTRIBUTION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //              method name: saveWorkRoom()
    // EFFECTS: saves the guess log to file
    private void saveLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(log);
            jsonWriter.close();
            System.out.println("Saved game state to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // ATTRIBUTION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //              method name: loadWorkRoom()
    // MODIFIES: this
    // EFFECTS: loads guess log from file
    private void loadLog() {
        try {
            tries = 0;
            log = jsonReader.read();
            answer = log.getGuessLog().get(0).getTargetWord();

            assessListOfGuess();
            userGuess.setBounds(80, 140 + ((tries) * 60), 240, 40);
            System.out.println("Loaded last game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }

    // MODIFIES: this
    // EFFECTS: for each guess in current list of guesses,
    //          - if it is the winning guess, changes cat image to won game
    //          - if no more tries, changes cat image to lost game
    private void assessListOfGuess() {
        for (Guess g : log.getGuessLog()) {
            newGuess = g;
            newGuess.generateColourCode(newGuess.getGuessWord());
            List<String> colours = colorGuess(newGuess.getColourCode());
            String finalString = setColour(colours);
            tries++;
            if (wonGame()) {
                catReaction("winnerCat");
                userGuess.setVisible(false);
            } else if (tries == 6) {
                catReaction("disgustedCat");
                userGuess.setVisible(false);
            }
            setNextLabel(finalString);
            userGuess.setText("");
        }
    }

    // EFFECTS: changes the colour of each character of the current guess
    //          - green if correct letter in correct position
    //          - yellow if correct letter in incorrect position
    //          - grey if incorrect letter
    public String setColour(List<String> colours) {
        String finalString = "";
        for (int i = 0; i < 5; i++) {
            String input = newGuess.getGuessWord();
            finalString = finalString + "<html><font face='Dialog' size='70' color=" + colours.get(i)
                    + "> " + input.charAt(i) + "</font> <font";
        }
        return finalString;
    }

    // MODIFIES: this
    // EFFECTS: sets up the initial frame, buttons, and generates a random answer from word bank
    public void runWordleAppGraphical() {
        setFrame();
        setTitle();
        setUserGuess();

        makeGuessButton();
        saveGameStateButton();
        loadGameButton();
        restartGameButton();
        hintButton();

        setLabels();
        frame.setVisible(true);
        String[] wordBankHard = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
        answer = wordBankHard[new Random().nextInt(wordBankHard.length)].toUpperCase();
    }

    // MODIFIES: this
    // EFFECTS: initializes labels representing empty spaces for where guesses will go
    private void setLabels() {
        labels = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            labels[i] = new JLabel("─ ─ ─ ─ ─");
            labels[i].setBounds(80, 120 + (i * 60), 400, 80);
            labels[i].setFont(new Font("Dialog", Font.BOLD, 60));
            labels[i].setFont(new Font("Dialog", Font.BOLD, 60));
            panel.add(labels[i]);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a new text field for where the user guess will be inputted
    private void setUserGuess() {
        userGuess = new JTextField();
        userGuess.setBounds(80, 140, 240, 40);
        userGuess.setFont(new Font("Dialog", Font.BOLD, 30));
        panel.add(userGuess);
    }

    // MODIFIES: this
    // EFFECTS: initializes the title and neutral image of cat friend
    private void setTitle() {
        panel.setLayout(null);
        title = new JLabel("Wordle ft. Cat Friend");
        title.setFont(new Font("Dialog", Font.BOLD, 40));
        title.setBounds(100, 30, 500, 70);

        cat = new JLabel("");
        catReaction("neutralCat");

        panel.add(title, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes new panel and frame with title 'Wordle'
    private void setFrame() {
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setTitle("Wordle");
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(this);

        frame.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the 'Make Guess' button and declares the action event upon clicking the button
    private void makeGuessButton() {
        JButton button = new JButton("Make Guess");
        button.setBounds(350, 180, 170, 50);

        // adds a guess to current list of guesses
        button.addActionListener(e -> enterGuess());
        panel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: initializes the 'Save Game' button and declares the action event upon clicking the button
    private void saveGameStateButton() {
        JButton button = new JButton("Save Game");
        button.setBounds(350, 300, 170, 50);
        // saves current state of game and prompts pop up
        button.addActionListener(e -> {
            for (Guess guess : log.getGuessLog()) {
                System.out.println(guess.getGuessWord());
            }
            saveLog();
            JOptionPane.showMessageDialog(null, "Data saved successfully.");
        });
        panel.add(button);

    }

    // MODIFIES: this
    // EFFECTS: initializes the 'Load Game' button and declares the action event upon clicking the button
    private void loadGameButton() {
        JButton button = new JButton("Load Game");
        button.setBounds(350, 360, 170, 50);
        // loads previously saved list of guess upon clicking
        button.addActionListener(e -> loadLog());
        panel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: initializes the 'Restart Game' button
    // clears all current guesses from list of guesses and resets the game to its initial state upon clicking
    private void restartGameButton() {
        JButton button = new JButton("Restart Game");
        button.setBounds(350, 420, 170, 50);
        button.addActionListener(new ActionListener() {
            // clears all current guesses from list of guesses and resets the game to its initial state upon clicking
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] wordBankHard = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
                answer = wordBankHard[new Random().nextInt(wordBankHard.length)].toUpperCase();
                tries = 0;
                log.clearLog();
                panel.removeAll();
                panel.repaint();

                setTitle();
                setUserGuess();
                makeGuessButton();
                saveGameStateButton();
                loadGameButton();
                restartGameButton();
                hintButton();
                setLabels();
            }
        });
        panel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: initializes the 'Hint' button;
    private void hintButton() {
        JButton button = new JButton("Hint");
        button.setBounds(350, 240, 170, 50);
        // upon clicking, reveals the first letter of the target word
        button.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "The word you are looking for begins with "
                        + answer.charAt(0) + "."));
        panel.add(button);
    }

    // EFFECTS: upon submitting a guess using the 'Make Guess' button, prompts further analysis of the guess
    @Override
    public void actionPerformed(ActionEvent e) {
        enterGuess();
    }


    // EFFECTS: if word length is not correct:
    //          prompts user to enter a 5 character,
    //          otherwise, submits the word for analyzing
    public void enterGuess() {
        if (userGuess.getText().length() != 5) {
            JOptionPane.showMessageDialog(null, "Please input a word with 5 characters :)");
            return;
        }
        submitWord();
    }


    // MODIFIES: this
    // EFFECTS: processes most current user guess;
    //          sets image of cat according to whether game is won, lost, or in progress
    //          clears text field after submitting one guess
    public void submitWord() {
        userGuess.setFont(new Font("Dialog", Font.BOLD, 30));
        userGuess.setBounds(80, 140 + ((tries + 1) * 60), 240, 40);

        String input = userGuess.getText().toUpperCase();
        newGuess = new Guess(input, answer);
        log.addGuessToLog(newGuess);

        newGuess.analyzeGuess();
        this.log.analyzeListOfGuess();

//        System.out.println(answer);
        List<String> colours = colorGuess(newGuess.getColourCode());

//        System.out.println("Set colors to " + colours.get(0) + " " + colours.get(1) + " " + colours.get(2) + " "
//                + colours.get(3) + " " + colours.get(4));
        String finalString = setColour(colours);
        tries++;
        if (wonGame()) {
            catReaction("winnerCat");
            userGuess.setVisible(false);
        } else if (tries == 6) {
            catReaction("disgustedCat");
            userGuess.setVisible(false);
        }
        setNextLabel(finalString);
        userGuess.setText("");
    }

    // MODIFIES: this
    // EFFECTS: displays cat image to the panel based on the reaction passed in as a parameter
    public void catReaction(String catReaction) {
        panel.remove(cat);
        repaint();
        ImageIcon icon = new ImageIcon("data/" + catReaction + ".png");
        cat = new JLabel(icon);
        cat.setBounds(500, 15, 700, 700);
        panel.add(cat);
        panel.repaint();
    }


    // EFFECTS: returns a list of strings representing what colour each character in a guess should change to
    //          based on its colour code
    public List<String> colorGuess(List<String> code) {
        List<String> colouredGuess = new ArrayList<>();
        for (int i = 0; i < newGuess.getGuessWord().length(); i++) {
            if (!code.contains(Integer.toString(i))) {
                colouredGuess.add("grey");
            } else {
                if (code.get(code.indexOf(Integer.toString(i)) + 1).equals("Y")) {
                    colouredGuess.add("orange");
                }
                if (code.get(code.indexOf(Integer.toString(i)) + 1).equals("G")) {
                    colouredGuess.add("green");
                }
            }
        }
        return colouredGuess;
    }

    // EFFECTS: creates a pop-up image upon winning the game
    public void popUpImage() {
        JFrame frame = new JFrame("Winner winner chicken dinner!");
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(new ImageIcon("data/winner!.jpg"));
        frame.add(label, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // EFFECTS: creates a pop-up image upon losing the game and reveals target word
    public void popUpLoser() {
        JFrame frame = new JFrame("Sorry :(, the correct word was: " + answer);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(new ImageIcon("data/sadCat.jpg"));
        frame.add(label, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets position of label based on the number of tries the user has used
    public void setNextLabel(String string) {
        labels[tries - 1].setText(string);
        labels[tries - 1].setBounds(80, (100 + (tries - 1) * 60), 250, 100);
    }

    // EFFECTS: prints description and date of each logged event to console
    public void printLog() {
        EventLog el = EventLog.getInstance();
        for (Event e : el) {
            System.out.println();
            System.out.println(e.getDate());
            System.out.println(e.getDescription());
        }
    }

    // EFFECTS: responds to event of opening a window
    @Override
    public void windowOpened(WindowEvent e) {
    }

    // EFFECTS: responds to event of closing a window
    @Override
    public void windowClosing(WindowEvent e) {
        printLog();
    }

    // EFFECTS: responds to event of closed window
    @Override
    public void windowClosed(WindowEvent e) {
    }

    // EFFECTS: responds to event of iconified window
    @Override
    public void windowIconified(WindowEvent e) {
    }

    // EFFECTS: responds to event of deiconified window
    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    // EFFECTS: responds to event of activating a window
    @Override
    public void windowActivated(WindowEvent e) {
    }

    // EFFECTS: responds to event of deactivating a window
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
