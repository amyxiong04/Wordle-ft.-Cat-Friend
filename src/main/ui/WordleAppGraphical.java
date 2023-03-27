package ui;

import model.Guess;
import model.Log;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordleAppGraphical extends JFrame implements ActionListener {
    public static final String DEFAULT = "\033[0m";
    public static final String YELLOW = "\033[0;33m";
    public static final String GREEN = "\033[0;32m";
    private static final String JSON_STORE = "./data/log.json";
    private int tries;
    private Boolean solved;
    private static String answer;
    private Guess newGuess;
    private int wordLength;
    private Log log;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static JPanel panel;
    private static JFrame frame;
//    private static WordPanel[] wordPanelArray;
//    private static UserPanel userPanel;

    private static JLabel title;
    private static JTextField userGuess;
    private static JLabel[] labels;
    private static JLabel cat;


    // EFFECTS: initializes Wordle game
    public WordleAppGraphical() {
        this.tries = 0;                                  // initializes six tries
        this.solved = false;                             // unsolved state
        this.answer = "";                                // initial target word
        this.newGuess = new Guess("", answer); // initial guess
        this.wordLength = 5;                             // initial word length
        this.log = new Log();                            // instantiates new guess log
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: sets the state of the game to solved
    public void setSolved() {
        this.solved = true;
    }

    // MODIFIES: this
    // EFFECTS: selects the appropriate target word with correct amount of characters depending on difficulty level
    public void selectAnswer(int i) {
        String[] wordBankEasy = {"BAT", "DOG", "SEA", "CAT", "BEE", "EYE", "TOP", "HAT", "EGG", "SUN", "JOY"};
        String[] wordBankMedium = {"MEAT", "BEST", "BANK", "CITY", "FISH", "MOON", "STAR", "ROSE", "JUMP"};
        String[] wordBankHard = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
        String[] wordBankImpossible = {"CHERRY", "ORANGE", "BANANA", "CANDLE", "DOCTOR", "MOTHER", "KITTEN"};

        switch (i) {
            case 1:
                this.answer = wordBankEasy[new Random().nextInt(wordBankEasy.length)].toUpperCase();
                break;
            case 2:
                this.answer = wordBankMedium[new Random().nextInt(wordBankMedium.length)].toUpperCase();
                break;
            case 3:
                this.answer = wordBankHard[new Random().nextInt(wordBankHard.length)].toUpperCase();
                break;
            case 4:
                this.answer = wordBankImpossible[new Random().nextInt(wordBankImpossible.length)].toUpperCase();
                break;
        }

    }

    // EFFECTS: prints out game instructions into console if selected by user
    public void provideGameInstructions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + "Welcome to Wordle (Java-Style)!");
        System.out.println("\n" + "[A] Game instructions \n" + "[B] No instructions needed \n"
                + "[C] Load previous game");
        String answer = scanner.nextLine();
        while (!answer.equalsIgnoreCase("A") && !answer.equalsIgnoreCase("B")
                && !answer.equalsIgnoreCase("C")) {
            System.out.println("Please select option A or B.");
//            answer = scanner.nextLine();
        }
        if (answer.equalsIgnoreCase("A")) {
            System.out.println(getInstructions() + "\n" + "\n" + "[A] I've got it!");
            String gotIt = scanner.nextLine();
            if (gotIt.equalsIgnoreCase("A")) {
                displayDifficulty();
            }
        }
        if (answer.equalsIgnoreCase("B")) {
            System.out.println("Okay, let's go!");
            displayDifficulty();
        }
        if (answer.equalsIgnoreCase("C")) {
            loadLog();
        }
    }

    // EFFECTS: returns Wordle game-play instructions
    public String getInstructions() {
        return "Your aim is to guess a word in six attempts. You may guess at the correct word by typing \n"
                + "in your guess and hitting 'Enter.' To guide you towards the target word, each time you \n"
                + "guess, certain letters will be coloured. A letter coloured in yellow means that the letter \n"
                + "is in the target word, but in the wrong position. A letter coloured in green indicates that \n"
                + "it is a correct letter and also in the correct position. If you fail to guess the target \n"
                + "word in 6 guesses, the word will be revealed.";
    }

    // EFFECTS: displays difficulty options in console
    public void displayDifficulty() {
        System.out.println();
        System.out.println("Please select a difficulty level: \n"
                + "[A] EASY       - Guess a 3 character word \n"
                + "[B] MEDIUM     - Guess a 4 character word \n"
                + "[C] HARD       - Guess a 5 character word \n"
                + "[D] IMPOSSIBLE - Guess a 6 character word \n");
        userSelectDifficulty();
    }


    // MODIFIES: this
    // EFFECTS: selects the appropriate answer and guess length based on difficulty level
    public void selectAnswerForMode(int selectAnswerLevel, int selectGuessLength, String mode) {
        selectAnswer(selectAnswerLevel);
        log.setGuessLength(selectGuessLength);
        System.out.println("You selected " + mode + " mode.");
    }

    // EFFECTS: prompts the user to select a difficulty level
    public void userSelectDifficulty() {
        Scanner scanner = new Scanner(System.in);
        String difficulty = scanner.nextLine();
        switch (difficulty.toUpperCase()) {
            case "A":
                selectAnswerForMode(1, 3, "EASY");
                break;
            case "B":
                selectAnswerForMode(2, 4, "MEDIUM");
                break;
            case "C":
                selectAnswerForMode(3, 5, "HARD");
                break;
            case "D":
                selectAnswerForMode(4, 6, "IMPOSSIBLE");
                break;
            default:
                System.out.println("Please select an existing difficulty level.");
                userSelectDifficulty();
        }
    }


    // MODIFIES: this
    // EFFECTS: processes user input and reminds user of word length constraints if inputted guess exceeds allowed
    // number of characters
    public String getGuessFromUser() {
        Scanner scanner = new Scanner(System.in);
        wordLength = log.getGuessLength(); // length of guess
        tries = log.getTriesRemaining();
        System.out.println();
        System.out.println("You have " + tries + " tries remaining.");
        System.out.println("Please make a guess containing " + wordLength + " characters >");
        String input = scanner.nextLine().toUpperCase();
        while (input.length() != this.wordLength || input.isEmpty()) {
            System.out.println("Please input a guess with " + wordLength + " characters >");
            input = scanner.nextLine().toUpperCase();
        }
        return input;
    }

    // MODIFIES: this
    // EFFECTS: processes most current user guess
    public void processCurrentUserGuess() {
        String input = getGuessFromUser();
        this.newGuess = new Guess(input, answer);
        newGuess.analyzeGuess();
    }

    // MODIFIES: this
    // EFFECTS: adds current user guess to existing list of guesses and processes list of guesses
    public void updateListOfGuesses() {
        this.log.addGuessToLog(newGuess);
        this.log.analyzeListOfGuess();
        System.out.println(log.interpretColourCode());
    }

    // MODIFIES: this
    // EFFECTS: sets the stage for new game by providing game instructions and setting tries
    //          to number of tries remaining in log
    public void setStage() {
        provideGameInstructions();
        tries = log.getTriesRemaining();
    }

    // MODIFIES: this
    // EFFECTS: processes current guess and assesses whether game is solved
    public void runWordle() {
        setStage();
        while (!solved && tries != 6) {
            processCurrentUserGuess();
            List<String> code = newGuess.getColourCode();
            int greenCount = 0;
            for (String s : code) {
                if (s.equals("G")) {
                    greenCount++;
                }
            }
            updateListOfGuesses();
            if (greenCount == wordLength) {
                wonGame();
                break;
            }
            updateTriesRemaining();
            if (tries == 6 && !solved) {
                gameOver();
                break;
            }
            saveGameOrHint();
        }
    }

//    // EFFECTS: handles a game that has been won
//    public void wonGame() {
//        Scanner scanner = new Scanner(System.in);
//        setSolved();
//        System.out.println("Congrats! You guessed the target word.");
//        System.out.println("[V] View statistics");
//        if (scanner.nextLine().equalsIgnoreCase("V")) {
//            viewStats();
//        }
//    }

    public Boolean wonGame() {
        List<String> code = newGuess.getColourCode();
        int greenCount = 0;
        for (String s : code) {
            if (s.equals("G")) {
                greenCount++;
            }
        }
        if (greenCount == 5) {
            return true;
        }
        return false;
    }

    // EFFECTS: handles a game that has not been won
    public void gameOver() {
        System.out.println("Game over! The target word was: " + answer + ".");
    }

    // EFFECTS: prints out game play statistics upon a won game:
    //          - number of tries taken to correctly guess the target word
    //          - a chronological list of all guesses made during the game
    //          - the target word
    public void viewStats() {
        System.out.println("Tries taken: " + (7 - tries));
        List<String> guesses = new ArrayList<>();
        for (Guess g : log.getGuessLog()) {
            guesses.add(g.getGuessWord());
        }
        System.out.println("Guesses made: " + guesses);
        System.out.println("Target word: " + answer);
    }

    // MODIFIES: this
    // EFFECTS: updates the number of tries remaining after each guess
    public void updateTriesRemaining() {
        log.updateTries(log.getTriesRemaining() - 1);
        tries = log.getTriesRemaining();
    }

    // EFFECTS: prints out options that prompt user to either get a hint, save, save and quit
    //          or continue the game without saving
    public void saveGameOrHint() {
        System.out.println("[H] I need a hint");
        System.out.println("[S] Save game");
        System.out.println("[Q] Save and quit game");
        System.out.println("[Enter] Continue without saving");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input.toUpperCase()) {
            case "C":
                getGuessFromUser();
                break;
            case "H":
                System.out.println("The word you are looking for begins with " + answer.charAt(0) + ".");
                break;
            case "S":
                saveLog();
                break;
            case "Q":
                saveLog();
                setSolved();
                break;
        }
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

    public String setColour(List<String> colours) {
        String finalString = "";
        for (int i = 0; i < 5; i++) {
            String input = newGuess.getGuessWord();
            finalString = finalString + "<html><font face='Dialog' size='70' color=" + colours.get(i)
                    + "> " + input.charAt(i) + "</font> <font";
        }
        return finalString;
    }

    public void runWordleAppGraphical() {
        setFrame();
        setTitle();
        setUserGuess();

        makeGuessButton();
        saveGameStateButton();
        loadGameButton();
        restartGameButton();

        setLabels();
        frame.setVisible(true);
        String[] wordBankHard = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
        answer = wordBankHard[new Random().nextInt(wordBankHard.length)].toUpperCase();
    }

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

    private void setUserGuess() {
        userGuess = new JTextField();
        userGuess.setBounds(80, 140 + (0 * 25), 240, 40);
        userGuess.setFont(new Font("Dialog", Font.BOLD, 30));
        panel.add(userGuess);
    }

    private void setTitle() {
        panel.setLayout(null);
        title = new JLabel("Wordle ft. Cat Friend");
        title.setFont(new Font("Dialog", Font.BOLD, 40));
        title.setBounds(100, 30, 500, 70);

        ImageIcon icon = new ImageIcon("data/icon.jpg");
        frame.setIconImage(icon.getImage());

        cat = new JLabel("");
        catReaction("neutralCat");

        panel.add(title, BorderLayout.CENTER);
    }

    private void setFrame() {
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Wordle");
        frame.setLocationRelativeTo(null);

        JLabel sillyCat = new JLabel(new ImageIcon("data/sillyCat.png"));
        panel.add(sillyCat, BorderLayout.WEST);

        frame.add(panel);
    }

    private void makeGuessButton() {
        JButton button = new JButton("Make Guess");
        button.setBounds(350, 240, 170, 50);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterGuess();
            }
        });
        panel.add(button);
    }

    private void saveGameStateButton() {
        JTextField textField = new JTextField(20);
        JButton button = new JButton("Save Game");
        button.setBounds(350, 300, 170, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Guess guess : log.getGuessLog()) {
                    System.out.println(guess.getGuessWord());
                }
                saveLog();
                JOptionPane.showMessageDialog(null, "Data saved successfully.");
            }
        });
        panel.add(button);

    }

    private void loadGameButton() {
        JButton button = new JButton("Load Game");
        button.setBounds(350, 360, 170, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadLog();
            }
        });
        panel.add(button);
    }

    private void restartGameButton() {
        JButton button = new JButton("Restart Game");
        button.setBounds(350, 420, 170, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] wordBankHard = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
                answer = wordBankHard[new Random().nextInt(wordBankHard.length)].toUpperCase();
                tries = 0;
                log.getGuessLog().clear();
                panel.removeAll();
                panel.repaint();

                setTitle();
                setUserGuess();
                makeGuessButton();
                saveGameStateButton();
                loadGameButton();
                restartGameButton();
                setLabels();
            }
        });
        panel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        enterGuess();
    }


    public void enterGuess() {
        if (userGuess.getText().length() != 5) {
            JOptionPane.showMessageDialog(null, "Please input a word with 5 characters :)");
            return;
        }
        submitWord();
    }


    // MODIFIES: this
    // EFFECTS: processes most current user guess
    public void submitWord() {
        userGuess.setFont(new Font("Dialog", Font.BOLD, 30));
        userGuess.setBounds(80, 140 + ((tries + 1) * 60), 240, 40);
//        labels[tries - 1].setBounds(80, (100 + (tries - 1) * 60), 250, 100);
//        userGuess.setBounds(80, 140 + (0 * 25), 390, 60);

        String input = userGuess.getText().toUpperCase();
        newGuess = new Guess(input, answer);
        log.addGuessToLog(newGuess);

        newGuess.analyzeGuess();
        this.log.analyzeListOfGuess();

        System.out.println(answer);
        List<String> colours = colorGuess(newGuess.getColourCode());

        System.out.println("Set colors to " + colours.get(0) + " " + colours.get(1) + " " + colours.get(2) + " "
                + colours.get(3) + " " + colours.get(4));
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

    public void catReaction(String catReaction) {
        panel.remove(cat);
        repaint();
        ImageIcon icon = new ImageIcon("data/" + catReaction + ".png");
        cat = new JLabel(icon);
        cat.setBounds(500, 15, 700, 700);
        panel.add(cat);
        panel.repaint();
    }


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

    public void popUpImage() {
        JFrame frame = new JFrame("Winner winner chicken dinner!");
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(new ImageIcon("data/winner!.jpg"));
        frame.add(label, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void popUpLoser() {
        JFrame frame = new JFrame("Sorry :(, the correct word was: " + answer);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(new ImageIcon("data/sadCat.jpg"));
        frame.add(label, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void setNextLabel(String string) {
        labels[tries - 1].setText(string);
        labels[tries - 1].setBounds(80, (100 + (tries - 1) * 60), 250, 100);
//        labels[i].setBounds(80, 140 + (i * 70), 400, 80);
    }
}
