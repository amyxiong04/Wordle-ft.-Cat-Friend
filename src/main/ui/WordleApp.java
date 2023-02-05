package ui;

import model.Guess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Wordle application
public class WordleApp {
    private int tries;
    private List<String> colourCode;
    private Guess currentGuess;

    public WordleApp() {
        this.tries = 6;
        this.colourCode = new ArrayList<>();
        this.currentGuess = new Guess("");
        runWordle();
    }

    public void runWordle() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please type a word >");
        String guess = s.nextLine().toUpperCase();
        this.currentGuess = new Guess(guess);
        currentGuess.analyzeGuess();
    }

    public void interpretColourCode() {
        this.currentGuess.getColourCode();
        //System.out.println(currentGuess);

    }



}
