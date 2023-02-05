package ui;

import model.Guess;

import java.util.List;
import java.util.Scanner;

// Wordle application
public class WordleApp {
    public static final String DEFAULT = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    
    
    private int tries;
    private Guess currentGuess;
    private Boolean solved;

    public WordleApp() {
        this.tries = 6;
        this.currentGuess = new Guess("");
        this.solved = false;
    }

    public void generateColourCode() {
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
    
    public void setSolved() {
        this.solved = true;
    }

    public void runWordle() {
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

            if (greenCount == 5) {
                setSolved();
                
            }
            
            
        }
    }
}





