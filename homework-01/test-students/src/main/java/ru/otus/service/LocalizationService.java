package ru.otus.service;

/**
 * Service of text localization.
 *
 * @author Vadim Kurbatov
 */
public interface LocalizationService {

    String getLocalizedMessage(String source);
}
