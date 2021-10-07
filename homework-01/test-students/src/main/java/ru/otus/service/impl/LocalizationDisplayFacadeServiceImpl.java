package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.service.DisplayService;
import ru.otus.service.LocalizationDisplayFacadeService;
import ru.otus.service.LocalizationService;

@Service
@RequiredArgsConstructor
public class LocalizationDisplayFacadeServiceImpl implements LocalizationDisplayFacadeService {

    private final DisplayService displayService;

    private final LocalizationService localizationService;

    @Override
    public void showLocalizedText(String textPropertyFromLocalization) {
        final var localizedText = localizationService.getLocalizedMessage(textPropertyFromLocalization);
        displayService.showText(localizedText);
    }

    @Override
    public void showLocalizedText(String textPropertyFromLocalization, String... args) {
        final var localizedText = localizationService.getLocalizedMessage(textPropertyFromLocalization);
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
