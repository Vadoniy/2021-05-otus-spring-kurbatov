package ru.otus.service;

/**
 * Service of information output.
 *
 * @author Vadim Kurbatov
 */
public interface DisplayService {

    void showText(String textProperty);

    void showText(String textProperty, String... args);

    String getInputString();
}