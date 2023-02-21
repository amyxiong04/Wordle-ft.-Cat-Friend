package model;

import java.util.ArrayList;

public class Log {
    //    private ArrayList<String> log;
//    private String result;
    private ArrayList<Guess> guessLog;

    // MODIFIES: this
    // EFFECTS: adds guess to current list of guesses
    public void addGuessToLog(Guess g) {
        guessLog.add(g);
    }

    public void analyzeListOfGuess() {
        for (Guess g : guessLog) {
            g.generateColourCode(g.getGuessWord());
            System.out.println(g.getColourCode());
            System.out.println(g.getGuessWord());
//            g.getGuessWord();
        }
    }


    public Log() {
//        log = new ArrayList<>();
//        result = "";
        guessLog = new ArrayList<>();
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

