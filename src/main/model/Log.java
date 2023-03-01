package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;
import java.util.List;

// Represents a log having a list of guesses, list of colours in a guess, and current guess length
public class Log implements Writable {
    private ArrayList<Guess> guessLog;           // list of all previous guesses
    private ArrayList<String> colouredGuess;     // tracks which characters of a guess have been altered in colour
    private int guessLength;                     // number of characters in each guess
    private int triesRemaining;
    // ANSI escape codes retrieved from W3schools
    public static final String DEFAULT = "\033[0m";
    public static final String YELLOW = "\033[0;33m";
    public static final String GREEN = "\033[0;32m";


    // EFFECTS: constructs log with empty list of guesses, empty list of colours, and guess length of 5 characters
    public Log() {
        guessLog = new ArrayList<>();
        colouredGuess = new ArrayList<>(); // string representation of colours in a guess; eg. "grey", "yellow", "green"
        guessLength = 5;                   // initializing guess length
    }

    // Getters

    // EFFECTS: returns current guess length
    public int getGuessLength() {
        return this.guessLength;
    }

    // EFFECTS: returns current list of guesses
    public List<Guess> getGuessLog() {
        return this.guessLog;
    }

    // EFFECTS: returns current state of colours in guess
    public List<String> getColouredGuess() {
        return this.colouredGuess;
    }

    public int getTriesRemaining() {
        return triesRemaining;
    }

    // MODIFIES: this
    // EFFECTS: sets guess length to given integer i
    public void setGuessLength(int i) {
        this.guessLength = i;
    }

    // MODIFIES: this
    // EFFECTS: adds given guess to current list of guesses
    public void addGuessToLog(Guess g) {
        guessLog.add(g);
    }

    // EFFECTS: generates a colour code that specifies the position and colour it changes to for any character
    //          in a guess that changes colour; repeats for each guess contained in the guess log
    public void analyzeListOfGuess() {
        for (Guess g : guessLog) {
            g.generateColourCode(g.getGuessWord());
        }
    }

    // MODIFIES: this
    // EFFECTS: returns a string representation of the colour-rendered word for each guess in the log
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

    // MODIFIES: this
    // EFFECTS: changes the colour of specific characters in a guess using ANSI codes;
    //          keeps track of string representation of the colour at each character of a guess
    //          in the field colouredGuess
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

    // EFFECTS: inserts pipe delimiter between each character of a guess for aesthetic and comprehension purposes
    public String toString(List<String> renderedGuess) {
        // ATTRIBUTION: string builder technique to add separators to a string:
        // https://stackoverflow.com/questions/58928521/java-add-separator-to-a-string
        StringBuilder guessWithDelimiter = new StringBuilder();
        for (int i = 0; i < renderedGuess.size(); i++) {
            guessWithDelimiter.append(renderedGuess.get(i));
            if (!isLastCharacter(i, guessLength)) {
                guessWithDelimiter.append("|");
            }
            if (isLastCharacter(i, guessLength)) {
                guessWithDelimiter.append("\n");
            }
        }
        return guessWithDelimiter.toString();
    }


    // EFFECTS: returns true if index of given i is last character in guess with given guess length,
    //          false otherwise
    public boolean isLastCharacter(int i, int guessLength) {
        if ((i + 1) % guessLength == 0) {
            return true;
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("guess length", guessLength);
        json.put("coloured guess", new JSONArray(colouredGuess));
        json.put("tries remaining", triesRemaining);
        json.put("guess log", guessLogToJson());
        return json;
    }

    private JSONArray guessLogToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Guess g: guessLog) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }
}



