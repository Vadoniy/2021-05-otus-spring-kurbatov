package ru.otus.exception;

public class UnknownGenreException extends RuntimeException {

    public UnknownGenreException(String message) {
        super(message);
    }
}