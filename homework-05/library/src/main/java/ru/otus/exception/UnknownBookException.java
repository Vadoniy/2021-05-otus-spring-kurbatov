package ru.otus.exception;

public class UnknownBookException extends RuntimeException {

    public UnknownBookException(String message) {
        super(message);
    }
}
