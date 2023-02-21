package model;

import java.util.ArrayList;
import java.util.List;

public class Log {
    public static final String DEFAULT = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    private ArrayList<Guess> guessLog;
    private List<List<String>> listOfColourCode;

    public Log() {
        guessLog = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds guess to current list of guesses
    public void addGuessToLog(Guess g) {
        guessLog.add(g);
    }

    public void analyzeListOfGuess() {
        for (Guess g : guessLog) {
            g.generateColourCode(g.getGuessWord());
        }
    }

    // MODIFIES: this
    // EFFECTS : prints out colour-rendered word based on Wordle rules
    public void interpretColourCode() {

        for (Guess g : guessLog) {
            g.analyzeGuess();
            String guessWord = g.getGuessWord();
            List<String> code = g.getColourCode();
            System.out.println();
            for (int i = 0; i < guessWord.length(); i++) {
                if (!code.contains(Integer.toString(i))) {
                    String greyWord = DEFAULT + guessWord.charAt(i) + DEFAULT;
                    System.out.print(greyWord);
//                log.add(greyWord);
                } else {
                    if (code.get(code.indexOf(Integer.toString(i)) + 1) == "Y") {
                        String yellowWord = YELLOW + guessWord.charAt(i) + DEFAULT;
                        System.out.print(yellowWord);
//                    log.add(yellowWord);
                    }
                    if (code.get(code.indexOf(Integer.toString(i)) + 1) == "G") {
                        String greenWord = GREEN + guessWord.charAt(i) + DEFAULT;
                        System.out.print(greenWord);
//                    log.add(greenWord);
                    }
                }
            }
        }
    }


//
//    public void add(String str) {
//        log.add(str);
//    }
//
//
//    public String getResult() {
//        return this.result;
//    }
//
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
////        sb.append("[");
//        for (int i = 0; i < log.size(); i++) {
//            sb.append(log.get(i));
//            if (i < log.size() - 1) {
//                sb.append("|");
//            }
//        }
//        return sb.toString();
//    }
//
//    public void formatPrint() {
//        System.out.println(toString());

//        String str = toString();
//        int len = str.length();
//
//        for (int i = 0; i < len; i++) {
//            System.out.print(str.charAt(i));
//            if ((i + 1) % 5 == 0) {
//                System.out.println();
//            }
//        }
}

