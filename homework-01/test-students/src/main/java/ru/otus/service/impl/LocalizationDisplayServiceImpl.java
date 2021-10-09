package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.service.DisplayService;
import ru.otus.service.LocalizationDisplayService;
import ru.otus.service.LocalizationService;

@Service
@RequiredArgsConstructor
public class LocalizationDisplayServiceImpl implements LocalizationDisplayService {

    private final DisplayService displayService;

    private final LocalizationService localizationService;

    private final MessageSource messageSource;

    @Override
    public void showLocalizedText(String textProperty) {
        final var localizedText = localizationService.getLocalizedMessage(textProperty);
        displayService.showText(localizedText);
    }

    @Override
    public void showLocalizedText(String textProperty, String... args) {
        final var localizedText = localizationService.getLocalizedMessage(textProperty);
        final var localizedTextWithReplacedPlaceHolders = String.format(localizedText,
                args);
        displayService.showText(localizedTextWithReplacedPlaceHolders);
    }

    @Override
    public void showText(String textToShow) {
        displayService.showText(textToShow);
    }

    @Override
    public void showText(String textToShow, String... args) {
        displayService.showText(textToShow, args);
    }

    @Override
    public String getInputString() {
        return displayService.getInputString();
    }
}
