package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a guess having a target word, current guess, and colour code
public class Guess {
    private String targetWord;
    private String currentGuess;
    private List<String> colourCode;

    // EFFECTS: creates a guess with its corresponding colour code and sets answer
    public Guess(String makeGuess, String answer) {
        this.currentGuess = makeGuess;
        this.targetWord = answer;
        this.colourCode = new ArrayList<>();
    }

    // Getters
    public String getGuessWord() {
        return this.currentGuess;
    }

    public void setGuessWord(String guess) {
        this.currentGuess = guess;
    }

    public String getTargetWord() {
        return this.targetWord;
    }

    public void setTargetWord(String target) {
        this.targetWord = target;
    }

    public List<String> getColourCode() {
        return this.colourCode;
    }


    // MODIFIES: this
    // EFFECTS: clears current colour code and updates current guess to given guess
    public void generateColourCode(String guess) {
        this.colourCode.clear();
        setGuessWord(guess);
        analyzeGuess();
    }

    // MODIFIES: this
    // EFFECTS: Analyzes guess and updates colour code specifying characters at
    //          which positions must change to which colours
    public void analyzeGuess() {
        for (int i = 0; i < targetWord.length(); i++) {
            if (currentGuess.charAt(i) == targetWord.charAt(i)) {
                colourCode.addAll(Arrays.asList(Integer.toString(i), "G"));
            }
        }
        for (int i = 0; i < targetWord.length(); i++) {
            for (int j = 0; j < currentGuess.length(); j++) {
                if (currentGuess.charAt(j) == targetWord.charAt(i) && (i != j)) {
                    if (!colourCode.contains(Integer.toString(j))) {
                        colourCode.addAll(Arrays.asList(Integer.toString(j), "Y"));

                    }
                }
            }
        }
    }
}


