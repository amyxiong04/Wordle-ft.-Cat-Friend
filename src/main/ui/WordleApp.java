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
    private String answer;
    private Guess newGuess;
    private int wordLength;
    private Log log;

    // EFFECTS: creates a Wordle game with 6 tries and unsolved state
    public WordleApp() {
        this.tries = 6;
        this.solved = false;
        this.answer = "";
        this.newGuess = new Guess("", answer);
        this.wordLength = 5;
        this.log = new Log();
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

    public void selectAnswer(int i) {
        String[] wordBankEasy = {"BAT", "DOG", "SEA", "CAT", "BEE", "EYE", "TOP", "HAT"};
        String[] wordBankMedium = {"MEAT", "BEST"};
        String[] wordBankHard = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
        String[] wordBankImpossible = {"CHERRY", "ORANGE"};

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
            System.out.println("Your aim is to guess a word in six attempts.  \n"
                    + "You may guess at the correct word by typing in your guess and hitting 'Enter.' \n"
                    + "To guide you towards the target word, each time you guess, certain letters will be \n"
                    + "coloured. A letter coloured in yellow means that the letter is in the target word, but \n"
                    + "in the wrong position. A letter coloured in green indicates that it is a correct letter \n"
                    + "and also in the correct position. If you fail to guess the target \n"
                    + "word in 6 guesses, the word will be revealed.");
            System.out.println();
            System.out.println("[A] I've got it!");
            if (scanner.nextLine().equalsIgnoreCase("A")) {
                displayDifficulty();
            }
        }
        if (answer.equalsIgnoreCase("B")) {
            System.out.println("Okay, let's continue ~ ");
            displayDifficulty();
        }
    }

    // EFFECTS: returns word length and selects answer
    public void displayDifficulty() {
        System.out.println();
        System.out.println("Please select a difficulty level: \n"
                + "[A] EASY - Guess a 3 character word \n"
                + "[B] MEDIUM - Guess a 4 character word \n"
                + "[C] HARD - Guess a 5 character word \n"
                + "[D] IMPOSSIBLE - Guess a 6 character word \n");
        selectDifficulty();
    }

    public void selectDifficulty() {
        Scanner scanner = new Scanner(System.in);
        String difficulty = "";
        difficulty = scanner.nextLine();
        if (difficulty.equalsIgnoreCase("A")) {
            selectAnswer(1);
            log.setGuessLength(3);
            System.out.println("You selected EASY mode.");
        }
        if (difficulty.equalsIgnoreCase("B")) {
            selectAnswer(2);
            log.setGuessLength(4);
            System.out.println("You selected MEDIUM mode.");
        }
        if (difficulty.equalsIgnoreCase("C")) {
            selectAnswer(3);
            log.setGuessLength(5);
            System.out.println("You selected HARD mode.");
        }
        if (difficulty.equalsIgnoreCase("D")) {
            selectAnswer(4);
            log.setGuessLength(6);
            System.out.println("You selected IMPOSSIBLE mode.");
        }
    }

    // REQUIRES: user input is of type string and length > 0
    // MODIFIES: this
    // EFFECTS: processes user input
    public String getGuessFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please make a guess >");
        String input = scanner.nextLine().toUpperCase();
        this.wordLength = answer.length();

        while (input.length() != this.wordLength) {
            System.out.println("Please input a guess with the correct number of characters >");
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
    // EFFECTS: adds current user guess to existing list of guesses and processes/prints list of guesses
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
            if (greenCount == wordLength) {
                setSolved();
                System.out.println("Congrats! You guessed the target word in "
                        + (7 - this.tries) + " tries.");
            }
            tries = tries - 1;
            if (tries == 0 && !solved) {
                System.out.println("Sorry, the correct word was: " + this.answer + ".");
            }
        }
    }
}






