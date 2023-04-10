package persistence;

import org.json.JSONObject;

// Represents the Json conversion of this
public interface Writable {
    // ATTRIBUTION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
