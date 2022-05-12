package rag.mil.bis.exception;

public class EmptyDataException extends Exception {
    public EmptyDataException() { }

    public EmptyDataException(String fieldName) {
        super(fieldName + " is empty");
    }
}
