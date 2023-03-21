package persistence;

import org.json.JSONObject;

public interface Writable {
    // ATTRIBUTION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

// add documentations