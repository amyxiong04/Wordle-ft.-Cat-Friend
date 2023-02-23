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

    // MODIFIES: this
    // EFFECTS:
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

    // EFFECTS: Prints game instructions into console if selected by user
    public void provideGameInstructions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + "Welcome to Wordle (Java-Style)!");
        System.out.println("\n" + "I would like game instructions \n" + "[A] Yes! \n" + "[B] I'm all good.");
        String answer = scanner.nextLine();
        while (!answer.equalsIgnoreCase("A") && !answer.equalsIgnoreCase("B")) {
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
            System.out.println("Okay, let's continue ~ ");
            displayDifficulty();
        }
    }

    // EFFECTS: returns Wordle game-play instructions
    public String getInstructions() {
        return "Your aim is to guess a word in six attempts.  \n"
                + "You may guess at the correct word by typing in your guess and hitting 'Enter.' \n"
                + "To guide you towards the target word, each time you guess, certain letters will be \n"
                + "coloured. A letter coloured in yellow means that the letter is in the target word, but \n"
                + "in the wrong position. A letter coloured in green indicates that it is a correct letter \n"
                + "and also in the correct position. If you fail to guess the target \n"
                + "word in 6 guesses, the word will be revealed.";
    }

    // EFFECTS: displays difficulty options in interactive console
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

    // EFFECTS: allows the user to select a difficulty level
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


    // REQUIRES: user input is of type string and length > 0
    // MODIFIES: this
    // EFFECTS: processes user input
    public String getGuessFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You have " + tries + " tries left.");
        System.out.println();
        System.out.println("Please make a guess >");
        String input = scanner.nextLine().toUpperCase();
        this.wordLength = answer.length();

        while (input.length() != this.wordLength || input.isEmpty()) {
            System.out.println("Please input a guess with " + wordLength + " characters.");
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
                System.out.println("Game over! The correct word was: " + this.answer + ".");
            }
        }
    }
}






