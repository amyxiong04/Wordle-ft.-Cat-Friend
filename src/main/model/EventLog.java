package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of guess log events.
// Use the Singleton Design Pattern to ensure that there is only
// one EventLog in the system and that the system has global access
// to the single instance of the EventLog.

public class EventLog implements Iterable<Event> {
    // the only EventLog in the system (Singleton Design Pattern)
    private static EventLog theLog;
    private Collection<Event> events;


    // EFFECTS: initializes new empty list of events to prevent external construction (Singleton Design Pattern).
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: Gets instance of EventLog - creates it if it doesn't already exist (Singleton Design Pattern);
    // returns instance of EventLog
    // MODIFIES: this
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    // EFFECTS: adds an event to the event log
    // MODIFIES: this
    public void logEvent(Event e) {
        events.add(e);
    }


    // EFFECTS: clears the event log and logs the event
    // MODIFIES: this
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: returns the result of the iterator method on the event log
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
