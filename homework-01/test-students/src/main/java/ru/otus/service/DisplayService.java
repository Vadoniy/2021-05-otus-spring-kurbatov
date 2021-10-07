package ru.otus.service;

/**
 * Service of information output.
 *
 * @author Vadim Kurbatov
 */
public interface DisplayService {

    void showText(String textToShow);

    void showText(String textToShow, String... args);

    String getInputString();
}