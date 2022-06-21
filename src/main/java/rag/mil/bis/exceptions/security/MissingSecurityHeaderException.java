package rag.mil.bis.exceptions.security;

public class MissingSecurityHeaderException extends SecurityException {
    public MissingSecurityHeaderException(String headerName) {
        super(String.format("Missing security header '%s'", headerName));
    }
}
