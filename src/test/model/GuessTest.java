package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GuessTest {
    private Guess testGuess;

    @BeforeEach
    public void setUp() {
        testGuess = new Guess("", "SLICE");
    }

    @Test
    public void testConstructor() {
        assertEquals("", testGuess.getGuessWord());
        assertEquals("SLICE", testGuess.getTargetWord());
        assertEquals(0, testGuess.getColourCode().size());
    }


    @Test
    public void testGetters() {
        assertEquals("", testGuess.getGuessWord());
        assertEquals("SLICE", testGuess.getTargetWord());
        List<String> colourCode = new ArrayList<>();
        assertEquals(colourCode, testGuess.getColourCode());
    }

    @Test
    public void testGenerateColourCode() {
        testGuess.generateColourCode("GUESS");
        assertEquals("GUESS", testGuess.getGuessWord());
    }

    @Test
    public void testAnalyzeGuessAllGreen() {
        testGuess.setTargetWord("SLICE");
        testGuess.setGuessWord("SLICE");
        testGuess.analyzeGuess();
        ArrayList<String> code = new ArrayList<>();
        code.add("0");
        code.add("G");
        code.add("1");
        code.add("G");
        code.add("2");
        code.add("G");
        code.add("3");
        code.add("G");
        code.add("4");
        code.add("G");
        assertEquals(code, testGuess.getColourCode());
    }

    @Test
    public void testAnalyzeGuessNoGreenNoYellow() {
        testGuess.setTargetWord("MONEY");
        testGuess.setGuessWord("PIZZA");
        testGuess.analyzeGuess();
        ArrayList<String> code = new ArrayList<>();
        assertEquals(code, testGuess.getColourCode());
    }

    @Test
    public void testAnalyzeGuessOnlyYellow() {
        testGuess.setTargetWord("SLICE");
        testGuess.setGuessWord("RIVER");
        ArrayList<String> code = new ArrayList<>();
        code.add("1");
        code.add("Y");
        code.add("3");
        code.add("Y");
        testGuess.analyzeGuess();
        assertEquals(code, testGuess.getColourCode());
    }

    @Test
    public void testAnalyzeGuessOnlyyYellow() {
//        testGuess.setTargetWord("SISCX");
//        testGuess.setGuessWord("RSERS");
        testGuess.setTargetWord("SERIES");
        testGuess.setGuessWord("TASSLE");
        ArrayList<String> code = new ArrayList<>();
//        code.add("1");
//        code.add("Y");
//        code.add("4");
//        code.add("Y");
        code.add("2");
        code.add("Y");
        code.add("3");
        code.add("Y");
        code.add("5");
        code.add("Y");

        testGuess.analyzeGuess();
        System.out.println(testGuess.getColourCode());
        assertEquals(code, testGuess.getColourCode());
    }

    @Test
    public void testAnalyzeGuessBothGreenAndYellow() {
        testGuess.setTargetWord("SLICE");
        testGuess.setGuessWord("SUPER");
        ArrayList<String> code = new ArrayList<>();
        code.add("0");
        code.add("G");
        code.add("3");
        code.add("Y");
        testGuess.analyzeGuess();
        assertEquals(code, testGuess.getColourCode());
    }



}