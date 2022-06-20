package rag.mil.bis.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("No such event");
    }

    public EventNotFoundException(long id) {
        super("No event with id " + id);
    }
}
