package model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

// Represents a log for all previous guesses
public class Log {
    private ArrayList<Guess> guessLog;              // list of all previous guesses
    private ArrayList<String> colouredGuess;        // tracks which characters of a guess have been altered in colour
    private int guessLength;                        // number of characters in each guess
    public static final String DEFAULT = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";

    public Log() {
        guessLog = new ArrayList<>();
        colouredGuess = new ArrayList<>();
        guessLength = 5;                    // Initializing guess length
    }

    public void setGuessLength(int i) {
        this.guessLength = i;
    }

    // MODIFIES: this
    // EFFECTS: adds given guess to current list of guesses
    public void addGuessToLog(Guess g) {
        guessLog.add(g);
    }

    // EFFECTS: returns current list of guesses
    public List<Guess> getGuessLog() {
        return this.guessLog;
    }

    // EFFECTS: returns current state of colours in guess
    public List<String> getColouredGuess() {
        return this.colouredGuess;
    }

    // EFFECTS: generates a colour code for each user guess preserved
    // in the guess log (list of guesses)
    public void analyzeListOfGuess() {
        for (Guess g : guessLog) {
            g.generateColourCode(g.getGuessWord());
        }
    }

    // MODIFIES: this
    // EFFECTS: returns a string representation of the colour-rendered word
    public String interpretColourCode() {
        List<String> renderedGuess = new ArrayList<>();
        for (Guess g : guessLog) {
            g.analyzeGuess();
            String guessWord = g.getGuessWord();
            List<String> code = g.getColourCode();
            renderGuess(guessWord, code, renderedGuess);
        }
        return toString(renderedGuess);
    }

    public void renderGuess(String guessWord, List<String> code, List<String> renderedGuess) {
        for (int i = 0; i < guessWord.length(); i++) {
            if (!code.contains(Integer.toString(i))) {
                String greyWord = DEFAULT + guessWord.charAt(i) + DEFAULT;
                colouredGuess.add("grey");
                renderedGuess.add(greyWord);
            } else {
                if (code.get(code.indexOf(Integer.toString(i)) + 1).equals("Y")) {
                    String yellowWord = YELLOW + guessWord.charAt(i) + DEFAULT;
                    colouredGuess.add("yellow");
                    renderedGuess.add(yellowWord);
                }
                if (code.get(code.indexOf(Integer.toString(i)) + 1).equals("G")) {
                    String greenWord = GREEN + guessWord.charAt(i) + DEFAULT;
                    colouredGuess.add("green");
                    renderedGuess.add(greenWord);
                }
            }
        }
    }

    public String toString(List<String> renderedGuess) {
        StringBuilder guessWithDelimiter = new StringBuilder();
        for (int i = 0; i < renderedGuess.size(); i++) {
            guessWithDelimiter.append(renderedGuess.get(i));
            if (i < renderedGuess.size() && !isLastCharacter(i, guessLength)) {
                guessWithDelimiter.append("|");
            }
            if (isLastCharacter(i, guessLength)) {
                guessWithDelimiter.append("\n");
            }
        }
        return guessWithDelimiter.toString();
    }

    // EFFECTS: returns true if index of given i is last character in guess; false otherwise
    public boolean isLastCharacter(int i, int guessLength) {
        if ((i + 1) % guessLength == 0) {
            return true;
        }
        return false;
    }
}



