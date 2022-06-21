package rag.mil.bis.exceptions;

public class EventNotFoundException extends BisServerRestException {
    public EventNotFoundException() {
        super("Event not found.");
    }

    public EventNotFoundException(long id) {
        super(String.format("No event with id = %d", id));
    }
}
