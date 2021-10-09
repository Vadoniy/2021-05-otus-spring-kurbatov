package ru.otus.service;

/**
 * Facade of display and localization services.
 *
 * @author Vadim Kurbatov
 */
public interface LocalizationDisplayService extends DisplayService {

    void showLocalizedText(String textProperty);

    void showLocalizedText(String textProperty, String... args);
}
