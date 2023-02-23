package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogTest {
    public static final String DEFAULT = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    private Log testGuessLog;
    private Guess G1;
    private Guess G2;
    private Guess G3;

    @BeforeEach
    public void setUp() {
        testGuessLog = new Log();
        G1 = new Guess("APPLE", "SLICE");
        G2 = new Guess("RIVER", "SLICE");
        G3 = new Guess("MONEY", "SLICE");
    }

    @Test
    public void testConstructor() {
        List<Guess> initialGuessLog = new ArrayList<>();
        List<Guess> guessLog = testGuessLog.getGuessLog();
        assertEquals(initialGuessLog, guessLog);

        List<String> initialStateOfColouredGuess = new ArrayList<>();
        List<String> colouredGuess = testGuessLog.getColouredGuess();
        assertEquals(initialStateOfColouredGuess, colouredGuess);

        assertEquals(5, testGuessLog.getGuessLength());
    }

    @Test
    public void testSetGuessLength() {
        testGuessLog.setGuessLength(3);
        assertEquals(3, testGuessLog.getGuessLength());
    }

    @Test
    public void testAddOneGuessToLog() {
        testGuessLog.addGuessToLog(G1);
        Guess guess = testGuessLog.getGuessLog().get(0);
        assertEquals(1, testGuessLog.getGuessLog().size());
        assertEquals(G1, guess);
    }

    @Test
    public void testAddMultipleGuessToLog() {
        testGuessLog.addGuessToLog(G1);
        testGuessLog.addGuessToLog(G2);
        testGuessLog.addGuessToLog(G3);
        assertEquals(3, testGuessLog.getGuessLog().size());
        assertEquals(G1, testGuessLog.getGuessLog().get(0));
        assertEquals(G2, testGuessLog.getGuessLog().get(1));
        assertEquals(G3, testGuessLog.getGuessLog().get(2));

    }

    @Test
    public void testAnalyzeListOfGuesses() {
        testGuessLog.addGuessToLog(G1);
        testGuessLog.addGuessToLog(G2);
        assertEquals(2, testGuessLog.getGuessLog().size());

        testGuessLog.analyzeListOfGuess();
        List<String> G1code = new ArrayList<>();
        G1code.add("4");
        G1code.add("G");
        G1code.add("3");
        G1code.add("Y");
        assertEquals(G1code, G1.getColourCode());

        List<String> G2code = new ArrayList<>();
        G2code.add("1");
        G2code.add("Y");
        G2code.add("3");
        G2code.add("Y");
    }

    @Test
    public void testRenderGuess() {
        List<String> renderedGuess = new ArrayList<>();
        testGuessLog.renderGuess(G1.getGuessWord(), G1.getColourCode(), renderedGuess);
        List<String> colouredGuess = testGuessLog.getColouredGuess();
        colouredGuess.add("grey");
        colouredGuess.add("grey");
        colouredGuess.add("grey");
        colouredGuess.add("yellow");
        colouredGuess.add("green");
        assertEquals(colouredGuess, testGuessLog.getColouredGuess());

//        String greyWord = DEFAULT + guessWord.charAt(i) + DEFAULT;
//        String yellowWord = YELLOW + guessWord.charAt(i) + DEFAULT;
//        String greenWord = GREEN + guessWord.charAt(i) + DEFAULT;
    }

    @Test
    public void testInterpretColourCode() {
        testGuessLog.addGuessToLog(G1);
        testGuessLog.addGuessToLog(G2);
        testGuessLog.addGuessToLog(G3);

        testGuessLog.interpretColourCode();
        List<String> colouredGuess = testGuessLog.getColouredGuess();
        // For guess: "APPLE"
        assertEquals("yellow", colouredGuess.get(3));
        assertEquals("green", colouredGuess.get(4));
        // For guess: "RIVER"
        assertEquals("yellow", colouredGuess.get(6));
        assertEquals("yellow", colouredGuess.get(8));
        // For guess: "MONEY"
        assertEquals("yellow", colouredGuess.get(13));
    }

    @Test
    public void testToString() {
        List<String> renderedGuess = new ArrayList<>();
        renderedGuess.add(DEFAULT + G1.getGuessWord().charAt(0) + DEFAULT);
        renderedGuess.add(DEFAULT + G1.getGuessWord().charAt(1) + DEFAULT);
        renderedGuess.add(DEFAULT + G1.getGuessWord().charAt(2) + DEFAULT);
        renderedGuess.add(YELLOW + G1.getGuessWord().charAt(3) + DEFAULT);
        renderedGuess.add(GREEN + G1.getGuessWord().charAt(4) + DEFAULT);

        String guessWithDelimiter = testGuessLog.toString(renderedGuess);
        String guess = renderedGuess.get(0) + "|" +
                renderedGuess.get(1) + "|" +
                renderedGuess.get(2) + "|" +
                renderedGuess.get(3) + "|" +
                renderedGuess.get(4);
        System.out.println(guess);
        System.out.println(guessWithDelimiter);

        assertEquals(guess, guess);

    }

    @Test
    public void testIsLastCharacter() {
        assertTrue(testGuessLog.isLastCharacter(4, 5));
        assertTrue(testGuessLog.isLastCharacter(2, 3));
        assertTrue(testGuessLog.isLastCharacter(3, 4));
        assertTrue(testGuessLog.isLastCharacter(5, 6));
        assertFalse(testGuessLog.isLastCharacter(3, 5));
        assertFalse(testGuessLog.isLastCharacter(1,3));
        assertFalse(testGuessLog.isLastCharacter(2, 4));
        assertFalse(testGuessLog.isLastCharacter(4, 6));
    }

}