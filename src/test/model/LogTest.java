package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogTest {
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
    public void testIsLastCharacter() {
        assertTrue(testGuessLog.isLastCharacter(4));
        assertTrue(testGuessLog.isLastCharacter(9));
        assertFalse(testGuessLog.isLastCharacter(8));
        assertFalse(testGuessLog.isLastCharacter(0));
    }

}