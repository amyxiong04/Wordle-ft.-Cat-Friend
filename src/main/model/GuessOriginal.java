//package model;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//// Represents a single five-letter word guess
//public class GuessOriginal {
//    private String targetWord;
//    private String guessword;
//    private List<String> colourCode;
//
//    // EFFECTS: creates a guess with its corresponding colour code and sets answer
//    public GuessOriginal(String makeGuess, String answer) {
//        this.guessword = makeGuess;
//        this.targetWord = answer;
//        this.colourCode = new ArrayList<>();
//    }
//
//    // Getters
//    public String getGuessWord() {
//        return this.guessword;
//    }
//
//    public String getTargetWord() {
//        return this.targetWord;
//    }
//
//    public List<String> getColourCode() {
//        return this.colourCode;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Analyzes guess and creates colour code specifying characters at
//    //          which positions must change to which colours
//    public void analyzeGuess() {
//        for (int i = 0; i < targetWord.length(); i++) {
//            if (guessword.charAt(i) == targetWord.charAt(i)) {
//                colourCode.addAll(Arrays.asList(Integer.toString(i), "G"));
//            }
//        }
//        for (int i = 0; i < targetWord.length(); i++) {
//            for (int j = 0; j < guessword.length(); j++) {
//                if (guessword.charAt(j) == targetWord.charAt(i) && (i != j)) {
//                    colourCode.addAll(Arrays.asList(Integer.toString(j), "Y"));
//                }
//            }
//        }
//    }
//
//
////    public static void main(String[] args) {
////        Guess myobj = new Guess("SLICE");
////        myobj.analyzeGuess();
////        myobj.getColourCode();
////    }
//
//
//    //high volume interger set vs low volume. abstraction 7 last vid.
//
//}

