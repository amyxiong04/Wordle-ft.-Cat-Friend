package persistence;

import org.json.JSONObject;

// Represents the Json conversion of this
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
