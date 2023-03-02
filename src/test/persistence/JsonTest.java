package persistence;

import model.Log;
import model.Guess;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkGuess(String makeGuess, String answer, Guess guess) {
        assertEquals(makeGuess, guess.getGuessWord());
        assertEquals(answer, guess.getTargetWord());
    }
}
