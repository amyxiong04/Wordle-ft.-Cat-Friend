package persistence;

import model.Guess;
import model.Log;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Log log = new Log();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // all good
        }
    }

    @Test
    void testJsonWriterEmptyGuessLog() {
        try {
            Log log = new Log();
            JsonWriter writer = new JsonWriter("./data/testJsonReaderEmptyGuessLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonReaderEmptyGuessLog.json");
            log = reader.read();
            assertEquals(0, log.getGuessLog().size());
            assertEquals(0, log.getColouredGuess().size());
            assertEquals(5, log.getGuessLength());
            assertEquals(6, log.getTriesRemaining());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testJsonWriterNonEmptyGuessLog() {
        try {
            Log log = new Log();
            log.addGuessToLog(new Guess("SLICE", "PIZZA"));
            log.addGuessToLog(new Guess("APPLE", "PIZZA"));
            JsonWriter writer = new JsonWriter("./data/testJsonReaderNonEmptyGuessLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonReaderNonEmptyGuessLog.json");
            log = reader.read();
            assertEquals(5, log.getGuessLength());
            List<Guess> guessLog = log.getGuessLog();
            assertEquals(2, log.getGuessLog().size());
            checkGuess("SLICE", "PIZZA", guessLog.get(0));
            checkGuess("APPLE", "PIZZA", guessLog.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
