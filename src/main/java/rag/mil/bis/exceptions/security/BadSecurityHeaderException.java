package rag.mil.bis.exceptions.security;

public class BadSecurityHeaderException extends SecurityException {
    public BadSecurityHeaderException(String headerName) {
        super(String.format("Wrong '%s' header value.", headerName));
    }
}
