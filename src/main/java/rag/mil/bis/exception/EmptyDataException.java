package rag.mil.bis.exception;

public class EmptyDataException extends RuntimeException {
    public EmptyDataException() {
    }

    public EmptyDataException(String fieldName) {
        super(fieldName + " is empty");
    }
}
