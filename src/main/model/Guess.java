package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a single five-letter word guess
public class Guess {
    private String targetWord;
    private String currentGuess;
    private List<String> colourCode;
    private String guess;
//    private String[] wordBank = {"HELLO", "SLICE", "APPLE"};
//    , "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
//    private String answer = wordBank[new Random().nextInt(wordBank.length)].toUpperCase();

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

    public String getTargetWord() {
        return this.targetWord;
    }

    public List<String> getColourCode() {
        return this.colourCode;
    }

//    public void generateColourCode() {
//        WordleApp wordleApp = new WordleApp();
//        String guess = wordleApp.getGuessFromUser();
//        this.currentGuess = guess;
//        System.out.println("guess" + guess);
//        System.out.println("target" + targetWord);
//        analyzeGuess();
//        System.out.println(colourCode);
//    }


    public void generateColourCode(String guess) {
//        WordleApp wordleApp = new WordleApp();
//        String guess = wordleApp.getGuessFromUser();
        colourCode.clear();
        this.currentGuess = guess;
//        System.out.println("guess" + guess);
//        System.out.println("target" + targetWord);
//        System.out.println("this.currentGUess is " + this.currentGuess);
        analyzeGuess();
//        System.out.println(colourCode);
    }

    // MODIFIES: this
    // EFFECTS: Analyzes guess and creates colour code specifying characters at
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


//    public static void main(String[] args) {
//        Guess myobj = new Guess("SLICE");
//        myobj.analyzeGuess();
//        myobj.getColourCode();
//    }


//high volume interger set vs low volume. abstraction 7 last vid.



