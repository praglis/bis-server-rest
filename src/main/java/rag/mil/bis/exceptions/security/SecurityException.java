package rag.mil.bis.exceptions.security;

import rag.mil.bis.exceptions.BisServerRestException;

public class SecurityException extends BisServerRestException {
    public SecurityException(String msg) {
        super(msg);
    }
}
