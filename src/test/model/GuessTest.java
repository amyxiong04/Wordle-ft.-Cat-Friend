package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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
    public void testAnalyzeGuess() {

    }

    @Test
    public void testGetters() {
        assertEquals("", testGuess.getGuessWord());
        assertEquals("SLICE", testGuess.getTargetWord());
        List<String> colourCode = new ArrayList<>();
        assertEquals(colourCode, testGuess.getColourCode());
    }

}