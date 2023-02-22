package ui;

import model.Guess;
import model.Log;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Wordle application
public class WordleApp {
    private int tries;
    private Boolean solved;
    private final String[] wordBank = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};

    private final String answer = wordBank[new Random().nextInt(wordBank.length)].toUpperCase();
    private Guess newGuess;
    private int wordLength;
    private Log log;

    // EFFECTS: creates a Wordle game with 6 tries and unsolved state
    public WordleApp() {
        this.tries = 6;
        this.solved = false;
        this.newGuess = new Guess("", answer);
        this.log = new Log();
        this.wordLength = 5;
    }

    public Guess getCurrentGuess() {
        return this.newGuess;
    }

    public int getTriesRemaining() {
        return this.tries;
    }

    public void setSolved() {
        this.solved = true;
    }

    public void provideGameInstructions() {
        String answer = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Welcome to Wordle (Java-Style)!");
        System.out.println();
        System.out.println("I would like game instructions \n"
                + "[A] Yes! \n"
                + "[B] I'm all good.");
        answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("A")) {
            System.out.println("Your aim is to guess a five-letter word in six attempts.  \n"
                    + "You may guess at the correct word by typing in a five-letter word and hitting 'Enter.' \n"
                    + "To guide you towards the target word, each time you guess, certain letters will be \n"
                    + "coloured. A letter coloured in yellow means that the letter is in the target word, but \n"
                    + "in the wrong position. A letter coloured in green indicates that it is a correct letter \n"
                    + "and also in the correct position. If you fail to guess the target \n"
                    + "word in 6 guesses, the word will be revealed. Good luck!");
        }
        if (answer.equalsIgnoreCase("B")) {
            System.out.println("Okay ~ let's continue");
        }
    }

    // REQUIRES: user input is of type string and length > 0
    // MODIFIES: this
    // EFFECTS: processes user input
    public String getGuessFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please make a guess >");
        String input = scanner.nextLine().toUpperCase();
//        this.newGuess = new Guess(input, answer);

        while (input.length() != wordLength) {
            System.out.println("Your guess is not five letters. Please input a five-letter word >");
            input = scanner.nextLine().toUpperCase();
        }
        return input;
    }

    // MODIFIES: this
    // EFFECTS: processes most current user guess
    public void processCurrentUserGuess() {
        WordleApp wordleApp = new WordleApp();
        String input = wordleApp.getGuessFromUser();
        this.newGuess = new Guess(input, answer);
        newGuess.analyzeGuess();
    }

    // MODIFIES: this
    // EFFECTS: adds current user guess to existing list of guesses and processes/renders list of guesses
    public void updateListOfGuesses() {
        this.log.addGuessToLog(newGuess);
        this.log.analyzeListOfGuess();
        System.out.println(this.log.interpretColourCode());
    }


    // move this function to guess
    // MODIFIES: this
    // EFFECTS: for each guess, assesses whether game is solved, and subtracts a try
    public void runWordle() {
        provideGameInstructions();
        while (!solved && tries > 0) {
            System.out.println();
            processCurrentUserGuess();
            List<String> code = newGuess.getColourCode();
            int greenCount = 0;
            for (String s : code) {
                if (s.equals("G")) {
                    greenCount++;
                }
            }
            System.out.println();
            updateListOfGuesses();
            if (greenCount == answer.length()) {
                setSolved();
                System.out.println("Congrats! You guessed the target word in " + (7 - this.tries) + " tries.");
            }
            tries = tries - 1;
            if (tries == 0 && !solved) {
                System.out.println("Sorry, the correct word was: " + this.answer + ".");
            }
        }
    }
}






