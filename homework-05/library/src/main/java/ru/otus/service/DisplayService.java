package ru.otus.service;

public interface DisplayService {

    void showText(String textToShow);

    void showText(String textToShow, String... args);

    String getInputString();
}
