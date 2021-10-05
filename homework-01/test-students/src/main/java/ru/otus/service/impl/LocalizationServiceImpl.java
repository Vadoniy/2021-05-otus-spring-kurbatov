package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.configuration.BusinessConfigurationProperties;
import ru.otus.service.LocalizationService;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final BusinessConfigurationProperties businessConfigurationProperties;

    private final MessageSource messageSource;

    @Override
    public String getLocalizedMessage(String source) {
        return messageSource.getMessage(source, new Object[]{}, Locale.forLanguageTag(businessConfigurationProperties.getLocale()));
    }
}
