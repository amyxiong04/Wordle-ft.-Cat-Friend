//package ui;
//
//import model.Guess;
//
//import java.util.List;
//import java.util.Random;
//import java.util.Scanner;
//
//// Wordle application
//public class WordleAppOriginal {
//    public static final String DEFAULT = "\u001B[0m";
//    public static final String YELLOW = "\u001B[33m";
//    public static final String GREEN = "\u001B[32m";
//    private String[] wordBank = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
//    private String answer = wordBank[new Random().nextInt(wordBank.length)].toUpperCase();
//
//
//    private int tries;
//    private Guess currentGuess;
//    private Boolean solved;
//
//    // EFFECTS: creates a Wordle game with 6 tries and unsolved state
//    public WordleAppOriginal() {
//        this.tries = 6;
//        this.currentGuess = new Guess("", answer);
//        this.solved = false;
//    }
//
//    public String getAnswer() {
//        return this.answer;
//    }
//
//    public Guess getCurrentGuess() {
//        return this.currentGuess;
//    }
//
//    public int getTriesRemaining() {
//        return this.tries;
//    }
//
//    // REQUIRES: user input is of type string and length > 0
//    // MODIFIES: this
//    // EFFECTS: processes user input
//    public void generateColourCode() {
//        Scanner s = new Scanner(System.in);
//        System.out.println("Please type a word >");
//        String guess = s.nextLine().toUpperCase();
//        this.currentGuess = new Guess(guess, answer);
//        currentGuess.analyzeGuess();
//    }
//
//    // MODIFIES: this
//    // EFFECTS : prints out colour-rendered word based on Wordle rules
//    public void interpretColourCode() {
//        List<String> code = this.currentGuess.getColourCode();
//        //System.out.println(code);
//        String guessWord = this.currentGuess.getGuessWord();
//        for (int i = 0; i < guessWord.length(); i++) {
//            if (!code.contains(Integer.toString(i))) {
//                System.out.print(DEFAULT + guessWord.charAt(i) + DEFAULT);
//            } else {
//                if (code.get(code.indexOf(Integer.toString(i)) + 1) == "Y") {
//                    System.out.print(YELLOW + guessWord.charAt(i) + DEFAULT);
//                }
//                if (code.get(code.indexOf(Integer.toString(i)) + 1) == "G") {
//                    System.out.print(GREEN + guessWord.charAt(i) + DEFAULT);
//                }
//            }
//        }
//        System.out.println();
//    }
//
//    public void setSolved() {
//        this.solved = true;
//    }
//
//    // move this function to guess
//    // MODIFIES: this
//    // EFFECTS: for each guess, assesses whether game is solved, and subtracts a try
//    public void runWordle() {
//        while (!solved && tries > 0) {
//            generateColourCode();
//            interpretColourCode();
//            tries = tries - 1;
//
//            List<String> code = this.currentGuess.getColourCode();
//            int greenCount = 0;
//
//            for (String s : code) {
//                if (s.equals("G")) {
//                    greenCount++;
//                }
//            }
//
//            if (greenCount == answer.length()) {
//                setSolved();
//
//            }
//
//
//        }
//    }
//}





