package ru.otus.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.service.LocalizationService;

import java.util.Locale;

@Component
public class LocalizationServiceImpl implements LocalizationService {

    private final String locale;

    private final MessageSource messageSource;

    public LocalizationServiceImpl(@Value("${exam.locale}") String locale, MessageSource messageSource) {
        this.locale = locale;
        this.messageSource = messageSource;
    }

    @Override
    public String getLocalizedMessage(String source) {
        return messageSource.getMessage(source, new Object[]{}, Locale.forLanguageTag(locale));
    }
}
