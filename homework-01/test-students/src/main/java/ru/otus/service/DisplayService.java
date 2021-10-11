package ru.otus.service;

public interface DisplayService {

    void showText(String textProperty);

    void showText(String textProperty, String... args);

    String getInputString();
}