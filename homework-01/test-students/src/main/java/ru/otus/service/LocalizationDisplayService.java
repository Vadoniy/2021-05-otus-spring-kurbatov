package ru.otus.service;

public interface LocalizationDisplayService extends DisplayService, LocalizationService {

    void showLocalizedMessage(String textProperty);

    void showLocalizedMessage(String textProperty, String... args);
}
