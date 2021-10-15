package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.service.DisplayService;
import ru.otus.service.LocalizationDisplayService;
import ru.otus.service.LocalizationService;

@Component
@RequiredArgsConstructor
public class LocalizationDisplayServiceImpl implements LocalizationDisplayService {

    private final DisplayService displayService;

    private final LocalizationService localizationService;

    @Override
    public void showLocalizedMessage(String textProperty) {
        final var textToShow = localizationService.getLocalizedMessage(textProperty);
        displayService.showText(textToShow);
    }

    @Override
    public void showLocalizedMessage(String textProperty, String... args) {
        final var textToShow = localizationService.getLocalizedMessage(textProperty);
        displayService.showText(textToShow, args);
    }

    @Override
    public void showText(String textProperty) {
        displayService.showText(textProperty);
    }

    @Override
    public void showText(String textProperty, String... args) {
        displayService.showText(textProperty, args);
    }

    @Override
    public String getInputString() {
        return displayService.getInputString();
    }

    @Override
    public String getLocalizedMessage(String source) {
        return localizationService.getLocalizedMessage(source);
    }
}
