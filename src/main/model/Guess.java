package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// Represents a single five-letter word guess
public class Guess {
    private String targetWord;
    private String guessword;
    private List<String> colourCode;

    // EFFECTS: creates a guess with corresponding colour code
    public Guess(String makeGuess, String answer) {
        this.guessword = makeGuess;
        this.targetWord = answer;
        this.colourCode = new ArrayList<>();
    }

    // Getters
    public String getGuessWord() {
        return this.guessword;
    }

    public List<String> getColourCode() {
        return this.colourCode;
    }

    // MODIFIES: this
    // EFFECTS: Analyzes guess and creates colour code specifying which colour
    // a character at a specific position must change to
    public void analyzeGuess() {
        for (int i = 0; i < targetWord.length(); i++) {
            if (guessword.charAt(i) == targetWord.charAt(i)) {
                colourCode.addAll(Arrays.asList(Integer.toString(i), "G"));
            }
        }
        for (int i = 0; i < targetWord.length(); i++) {
            for (int j = 0; j < guessword.length(); j++) {
                if (guessword.charAt(j) == targetWord.charAt(i) && (i != j)) {
                    colourCode.addAll(Arrays.asList(Integer.toString(j), "Y"));
                }
            }
        }
    }

//    public static void main(String[] args) {
//        Guess myobj = new Guess("SLICE");
//        myobj.analyzeGuess();
//        myobj.getColourCode();
//    }

}

