package ru.otus.service;

/**
 * Facade of display and localization services.
 *
 * @author Vadim Kurbatov
 */
public interface LocalizationDisplayFacadeService extends DisplayService {

    void showLocalizedText(String textToShow);

    void showLocalizedText(String textToShow, String... args);
}
