package persistence;

import model.Guess;
import model.Log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Log read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses log from JSON object and returns it
    private Log parseLog(JSONObject jsonObject) {
        int guessLength = (int) jsonObject.get("guess length");
        JSONArray colouredGuess = (JSONArray) jsonObject.get("coloured guess");
        int triesRemaining = (int) jsonObject.get("tries remaining");
        Log log = new Log();
        log.updateTries(triesRemaining);
        addGuesses(log, jsonObject);
        return log;
    }

    // MODIFIES: log
    // EFFECTS: parses guesses from JSON object and adds them to log
    private void addGuesses(Log log, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("guess log");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addGuess(log, nextThingy);
        }
    }

    // MODIFIES: log
    // EFFECTS: parses guess from JSON object and adds it to log
    private void addGuess(Log log, JSONObject jsonObject) {
        String currentGuess = jsonObject.getString("current guess");
        String targetWord = jsonObject.getString("target word");
        JSONArray colourCode = (JSONArray) jsonObject.get("colour code");
        Guess g = new Guess(currentGuess, targetWord);
        log.addGuessToLog(g);
    }
}