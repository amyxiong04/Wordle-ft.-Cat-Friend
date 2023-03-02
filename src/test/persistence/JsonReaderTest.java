package persistence;

import model.Guess;
import model.Log;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Log log = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testJsonReaderEmptyGuessLog() {
        JsonReader reader = new JsonReader("./data/testJsonReaderEmptyGuessLog.json");
        try {
            Log log = reader.read();
            assertEquals(0, log.getGuessLog().size());
            assertEquals(0, log.getColouredGuess().size());
            assertEquals(5, log.getGuessLength());
            assertEquals(6, log.getTriesRemaining());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testJsonReaderNonEmptyGuessLog() {
        JsonReader reader = new JsonReader("./data/testJsonReaderNonEmptyGuessLog.json");
        try {
            Log log = reader.read();
            assertEquals(5, log.getGuessLength());
            List<Guess> guessLog = log.getGuessLog();
            assertEquals(2, guessLog.size());
            checkGuess("SLICE", "PIZZA", guessLog.get(0));
            checkGuess("APPLE", "PIZZA", guessLog.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
