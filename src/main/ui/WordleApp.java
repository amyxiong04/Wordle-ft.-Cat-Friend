package ui;

import model.Guess;
import model.Log;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Wordle application
public class WordleApp {
    private static final String JSON_STORE = "./data/log.json";
    private int tries;
    private Boolean solved;
    private String answer;
    private Guess newGuess;
    private int wordLength;
    private Log log;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: initializes Wordle game
    public WordleApp() {
        this.tries = 6;                                  // six tries
        this.solved = false;                             // unsolved state
        this.answer = "";                                // initial target word
        this.newGuess = new Guess("", answer); // initial guess
        this.wordLength = 5;                             // initial word length
        this.log = new Log();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);// instantiates new guess log
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
        System.out.println("\n" + "[A] I would like game instructions \n" + "[B] Take me to the game \n"
                + "[C] Load previous game");
        String answer = scanner.nextLine();
        while (!answer.equalsIgnoreCase("A") && !answer.equalsIgnoreCase("B") && !answer.equalsIgnoreCase("C")) {
            System.out.println("Please select option A or B.");
            answer = scanner.nextLine();
        }
        if (answer.equalsIgnoreCase("A")) {
            System.out.println(getInstructions() + "\n" + "\n" + "[A] I've got it!");
            String gotIt = scanner.nextLine();
            while (!gotIt.equalsIgnoreCase("A")) {
                System.out.println("Please select option A to indicate that you've got it.");
                gotIt = scanner.nextLine();
            }
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
        this.wordLength = answer.length();
        System.out.println("You have " + tries + " tries left.");
        System.out.println();
        System.out.println("Please make a guess containing " + this.wordLength + " characters >");
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
    // EFFECTS: processes current guess and assesses whether game is solved
    public void runWordle() {
        provideGameInstructions();
        while (!solved && tries > 0) {
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
                setSolved();
                System.out.println("Congrats! You guessed the target word in "
                        + (7 - this.tries) + " tries.");
            }
            tries = tries - 1;
            if (tries == 0 && !solved) {
                System.out.println("Game over! The correct word was: " + this.answer + ".");
            }
            saveGame();
        }
        System.out.println("Goodbye!");
    }

    public void saveGame() {
        System.out.println("[S] save current game state to file");
        System.out.println("[Q] quit");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input.toUpperCase()) {
            case "S":
                saveLog();
                break;
            case "Q":
                setSolved();
                break;
            default:
                System.out.println("Please select an existing option.");
        }
    }


    // EFFECTS: saves the list of guesses to file
    private void saveLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(log);
            jsonWriter.close();
            System.out.println("Saved current status to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadLog() {
        try {
            log = jsonReader.read();
            System.out.println(log.interpretColourCode());
            System.out.println("Loaded guess log from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }
}






