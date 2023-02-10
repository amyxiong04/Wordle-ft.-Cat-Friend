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
    public void testAnalyzeGuess() {
        testGuess.setTargetWord("SLICE");
        testGuess.setGuessWord("SLICE");
        testGuess.analyzeGuess();
        ArrayList<String> code = new ArrayList<>();
//        assertEquals(code.addAll(Arrays.asList("0", "G", "1", "G", "2", "G", "3", "G", "4", "G")),
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


}