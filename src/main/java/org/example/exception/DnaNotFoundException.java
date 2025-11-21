package org.example.exception;

public class DnaNotFoundException extends RuntimeException {
    public DnaNotFoundException(String message) {
        super(message);
    }
}