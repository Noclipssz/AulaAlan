package org.backend.alan_api.exception;

public class CpfExistenteException extends RuntimeException {
    public CpfExistenteException(String message) {
        super(message);
    }
}