package model;

import java.util.Calendar;
import java.util.Date;

// Represents a guess log event
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;


    // EFFECTS: creates an event with the given description and the current date/time stamp
    // MODIFIES: this
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // EFFECTS: gets the date of this event (includes time)
    public Date getDate() {
        return dateLogged;
    }

    // EFFECTS: gets the description of this event
    public String getDescription() {
        return description;
    }


    // EFFECTS: overrides equals such that the fields of an event are compared (dateLogged and description)
    // returns true if equal, false otherwise
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    // EFFECTS: overrides hashcode such that the hashcodes
    // of the fields of this event are compared; returns hashcode
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }


    // EFFECTS: returns string with the dateLogged of this event followed
    // by its description on the next line in console
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
