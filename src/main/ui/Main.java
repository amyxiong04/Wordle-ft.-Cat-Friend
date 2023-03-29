package ui;

import java.util.Scanner;

// Initializes the console wordle application, allowing the user to play again if they wish
public class Main {
    public static void main(String[] args) {
        Boolean userWantsToPlayAgain = null;
        do {
            WordleApp wordle = new WordleApp();
            wordle.runWordle();
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("[P] Play again");
            if (scanner.nextLine().equalsIgnoreCase("P")) {
                userWantsToPlayAgain = true;
            }
        } while (userWantsToPlayAgain);
    }
}
