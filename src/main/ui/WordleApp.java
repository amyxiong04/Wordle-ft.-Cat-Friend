package ui;

import model.Guess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Wordle application
public class WordleApp {
    public static final String GREY = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";

    private int tries;
    private List<String> colourCode;
    private Guess currentGuess;

    public WordleApp() {
        this.tries = 6;
        this.colourCode = new ArrayList<>();
        this.currentGuess = new Guess("");
    }

    public void runWordle() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please type a word >");
        String guess = s.nextLine().toUpperCase();
        this.currentGuess = new Guess(guess);
        currentGuess.analyzeGuess();
    }

    public void interpretColourCode() {
        List<String> code = this.currentGuess.getColourCode();
        System.out.println(code);
        String guessWord = this.currentGuess.getGuessword();
//        for (int i = 0; i < code.size(); i += 2) {
//            int num = Integer.valueOf(code.get(i));
//            if (code.get(i + 1) == "Y") {
//                System.out.print(YELLOW + guessWord.charAt(num) + GREY);
//            }
//            if (code.get(i + 1) == "G") {
//                System.out.print(GREEN + guessWord.charAt(num) + GREY);
//            }
//
//        }
        for (int i = 0; i < guessWord.length(); i++) {
            if (!code.contains(Integer.toString(i))) {
                System.out.print(GREY + guessWord.charAt(i) + GREY);
            } else {
                if (code.get(code.indexOf(Integer.toString(i)) + 1) == "Y") {
                    System.out.print(YELLOW + guessWord.charAt(i) + GREY);
                }
                if (code.get(code.indexOf(Integer.toString(i)) + 1) == "G") {
                    System.out.print(GREEN + guessWord.charAt(i) + GREY);
                }
            }
        }
    }
}





