package ru.otus.exception;

public class UnknownAuthorException extends RuntimeException {

    public UnknownAuthorException(String message) {
        super(message);
    }
}