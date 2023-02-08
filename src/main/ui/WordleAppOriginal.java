package ui;


import model.GuessOriginal;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Wordle application
public class WordleAppOriginal {
    public static final String DEFAULT = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    private String[] wordBank = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
    private String answer = wordBank[new Random().nextInt(wordBank.length)].toUpperCase();


    private int tries;
    private GuessOriginal currentGuess;
    private Boolean solved;

    // EFFECTS: creates a Wordle game with 6 tries and unsolved state
    public WordleAppOriginal() {
        this.tries = 6;
        this.currentGuess = new GuessOriginal("", answer);
        this.solved = false;
    }

    public String getAnswer() {
        return this.answer;
    }

    public GuessOriginal getCurrentGuess() {
        return this.currentGuess;
    }

    public int getTriesRemaining() {
        return this.tries;
    }

    public void setSolved() {
        this.solved = true;
    }

    public void provideGameInstructions() {
        System.out.println("Welcome to Wordle (Java-Style)!");
        System.out.println();
        System.out.println("The aim is to guess a five-letter word in six attempts. You may guess at the correct \n"
                + "word by typing in a five-letter word and hitting 'Enter.' To guide you towards the target word,\n"
                + "each time you guess, certain letters will be coloured. A letter coloured in yellow means that \n"
                + "the letter is in the target word, but in the wrong position. A letter coloured in green indicates \n"
                + "that it is a correct letter and also in the correct position. If you fail to guess the target \n"
                + "word in 6 guesses, the word will be revealed. Good luck!");
    }


    // REQUIRES: user input of type string
    // EFFECTS: returns user input
    public String getGuessFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please make a guess >");
        String input = scanner.nextLine().toUpperCase();
        currentGuess = new GuessOriginal(input, answer);
        return input;
    }


    // REQUIRES: user input is of type string and length > 0
    // MODIFIES: this
    // EFFECTS: processes user input
    public void generateColourCode() {
        currentGuess = new GuessOriginal(getGuessFromUser(), answer);
        currentGuess.analyzeGuess();
    }

    // MODIFIES: this
    // EFFECTS : prints out colour-rendered word based on Wordle rules
    public void interpretColourCode() {
        List<String> code = this.currentGuess.getColourCode();
        String guessWord = this.currentGuess.getGuessWord();
        for (int i = 0; i < guessWord.length(); i++) {
            if (!code.contains(Integer.toString(i))) {
                System.out.print(DEFAULT + guessWord.charAt(i) + DEFAULT);
            } else {
                if (code.get(code.indexOf(Integer.toString(i)) + 1) == "Y") {
                    System.out.print(YELLOW + guessWord.charAt(i) + DEFAULT);
                }
                if (code.get(code.indexOf(Integer.toString(i)) + 1) == "G") {
                    System.out.print(GREEN + guessWord.charAt(i) + DEFAULT);
                }
            }
        }
        System.out.println();
    }

    // move this function to guess
    // MODIFIES: this
    // EFFECTS: for each guess, assesses whether game is solved, and subtracts a try
    public void runWordle() {
        System.out.println();
        provideGameInstructions();
        System.out.println();
        sixAttempts();
    }

    public void sixAttempts() {
//        if (currentGuess.getGuessWord().length() > 5) {
//            System.out.println("Your guess is too long.");
//        } else if (currentGuess.getGuessWord().length() < 5) {
//            System.out.println("Your guess is too short.");
//        } else {
        while (!solved && tries > 0) {
            generateColourCode();
            interpretColourCode();
            tries = tries - 1;

            List<String> code = this.currentGuess.getColourCode();
            int greenCount = 0;

            for (String s : code) {
                if (s.equals("G")) {
                    greenCount++;
                }
            }
            if (greenCount == answer.length()) {
                setSolved();
            }
        }
    }
}


//    public void runWordle() {
//        while (!solved && tries > 0) {
//            generateColourCode();
//            interpretColourCode();
//            tries = tries - 1;
//
//            List<String> code = this.currentGuess.getColourCode();
//            int greenCount = 0;
//
//            for (String s : code) {
//                if (s.equals("G")) {
//                    greenCount++;
//                }
//            }
//
//            if (greenCount == answer.length()) {
//                setSolved();
//
//            }
//        }
//    }
//}






