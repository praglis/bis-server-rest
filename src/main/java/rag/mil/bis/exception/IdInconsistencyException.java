package rag.mil.bis.exception;

public class IdInconsistencyException extends BisServerRestException {
    public IdInconsistencyException(long bodyId, long pathId) {
        super(String.format("Id passed in body (%s) and through URL (%s) must be the same.", bodyId, pathId));
    }
}
