package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a guess having a target word, current guess, and colour code
public class Guess implements Writable {
    private String targetWord;
    private String currentGuess;
    private List<String> colourCode;

    // EFFECTS: constructs a guess with given input and sets target word to given answer;
    //          constructs empty colour code
    public Guess(String makeGuess, String answer) {
        this.currentGuess = makeGuess;
        this.targetWord = answer;
        this.colourCode = new ArrayList<>();
    }

    // Getters
    public String getGuessWord() {
        return this.currentGuess;
    }

    public String getTargetWord() {
        return this.targetWord;
    }

    public List<String> getColourCode() {
        return this.colourCode;
    }

    // MODIFIES: this
    // EFFECTS: sets current guess to given guess
    public void setGuessWord(String guess) {
        this.currentGuess = guess;
    }

    // MODIFIES: this
    // EFFECTS: sets current target word to given target word
    public void setTargetWord(String target) {
        this.targetWord = target;
    }


    // MODIFIES: this
    // EFFECTS: clears current colour code and updates current guess to given guess
    public void generateColourCode(String guess) {
        this.colourCode.clear();
        setGuessWord(guess);
        analyzeGuess();
    }

    // MODIFIES: this
    // EFFECTS: updates colour code specifying characters at
    //          which positions must change to which colours
    //          - green if correct character at correct position
    //          - yellow if correct character at incorrect position
    //          - default otherwise
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

    @Override
    // EFFECTS: returns guess as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("target word", targetWord);
        json.put("current guess", currentGuess);
        json.put("colour code", new JSONArray(colourCode));
        return json;
    }
}


