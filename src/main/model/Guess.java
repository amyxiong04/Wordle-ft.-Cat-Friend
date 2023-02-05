package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a single five-letter word guess
public class Guess {
    String[] wordBank = {"HELLO", "SLICE", "LEARN"};
    //    String answer = wordBank[new Random().nextInt(wordBank.length)].toUpperCase();
    String answer = "SLICE";
    private String targetWord;
    private String guessword;
    private List<String> colourCode;

    public Guess(String makeGuess) {
        this.guessword = makeGuess;
        this.targetWord = answer;
        this.colourCode = new ArrayList<>();
    }

    public List<String> getColourCode() {
        System.out.println(colourCode);
        return this.colourCode;
    }

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

