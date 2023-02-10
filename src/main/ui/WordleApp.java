package ui;

import model.Guess;
import model.Log;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Wordle application
public class WordleApp {
    public static final String DEFAULT = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    private int tries;
    private Boolean solved;
    private String[] wordBank = {"HELLO", "SLICE", "APPLE", "BREAD", "MONEY", "SPORT", "RIVER", "PIZZA"};
    //    private String answer = wordBank[new Random().nextInt(wordBank.length)].toUpperCase();
//    private String[] wordBank = {"HELLO", "SLICE", "APPLE"};
    private String answer = wordBank[new Random().nextInt(wordBank.length)].toUpperCase();
    //    private String answer = "HELLO";
    private Guess newGuess;
    private int wordLength;
    //private int difficultLevel;
    private Log log;

    // EFFECTS: creates a Wordle game with 6 tries and unsolved state
    public WordleApp() {
        this.tries = 6;
        this.solved = false;
        this.newGuess = new Guess("", answer);
        this.log = new Log();
        this.wordLength = answer.length();
    }

//
//    public Guess getCurrentGuess() {
//        return this.getGuessWord();
//    }

    public int getTriesRemaining() {
        return this.tries;
    }

    public void setSolved() {
        this.solved = true;
    }

    public void provideGameInstructions() {
        System.out.println("Welcome to Wordle (Java-Style)!");
        System.out.println("Hit enter to continue.");
        System.out.println("I would like game instructions \n"
                + "[A] Yes! \n"
                + "[B] Psh, I'm all good.");
        System.out.println("The aim is to guess a five-letter word in six attempts. You may guess at the correct \n"
                + "word by typing in a five-letter word and hitting 'Enter.' To guide you towards the target word,\n"
                + "each time you guess, certain letters will be coloured. A letter coloured in yellow means that \n"
                + "the letter is in the target word, but in the wrong position. A letter coloured in green indicates \n"
                + "that it is a correct letter and also in the correct position. If you fail to guess the target \n"
                + "word in 6 guesses, the word will be revealed. Good luck!");
    }

    public String processInput(String text) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(text);
        String input = scanner.nextLine();
        return input;
    }

    // REQUIRES: user input is of type string and length > 0
    // MODIFIES: this
    // EFFECTS: processes user input
    public String getGuessFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please make a guess >");
        String input = scanner.nextLine().toUpperCase();

        while (input.length() != wordLength) {
            System.out.println("Your guess is not five letters. Please input a five-letter word >");
            input = scanner.nextLine().toUpperCase();
        }
        return input;
    }


    // MODIFIES: this
    // EFFECTS : prints out colour-rendered word based on Wordle rules
    public void interpretColourCode(Log log) {

        List<String> code = this.newGuess.getColourCode();

        String guessWord = this.newGuess.getGuessWord();
        for (int i = 0; i < guessWord.length(); i++) {
            if (!code.contains(Integer.toString(i))) {
                String greyWord = DEFAULT + guessWord.charAt(i) + DEFAULT;
                System.out.print(greyWord);
                log.add(greyWord);
            } else {
                if (code.get(code.indexOf(Integer.toString(i)) + 1) == "Y") {
                    String yellowWord = YELLOW + guessWord.charAt(i) + DEFAULT;
                    System.out.print(yellowWord);
                    log.add(yellowWord);
                }
                if (code.get(code.indexOf(Integer.toString(i)) + 1) == "G") {
                    String greenWord = GREEN + guessWord.charAt(i) + DEFAULT;
                    System.out.print(greenWord);
                    log.add(greenWord);
                }
            }
        }
        System.out.println();
        String logOutput = log.toString();
        System.out.println(logOutput);
    }


    // move this function to guess
    // MODIFIES: this
    // EFFECTS: for each guess, assesses whether game is solved, and subtracts a try
    public void runWordle() {
        WordleApp wordleApp = new WordleApp();
        provideGameInstructions();
        System.out.println();
        while (!solved && tries > 0) {
            String input = wordleApp.getGuessFromUser();
            newGuess.generateColourCode(input);
            interpretColourCode(this.log);
            tries = tries - 1;

            List<String> code = newGuess.getColourCode();
            int greenCount = 0;

            for (String s : code) {
                if (s.equals("G")) {
                    greenCount++;
                }
            }
            if (greenCount == newGuess.getTargetWord().length()) {
                setSolved();
            }
        }
    }
}






