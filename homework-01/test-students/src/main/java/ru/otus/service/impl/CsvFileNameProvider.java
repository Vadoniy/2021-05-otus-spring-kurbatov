package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.otus.configuration.BusinessConfigurationProperties;
import ru.otus.service.FileNameProvider;

@Component
@RequiredArgsConstructor
public class CsvFileNameProvider implements FileNameProvider {

    private final BusinessConfigurationProperties businessConfigurationProperties;

    @Override
    public String getFileNameWithPath() {
        final var fileNameWithPath = new StringBuilder("classpath:")
                .append(businessConfigurationProperties.getFilePath())
                .append(businessConfigurationProperties.getFileName());
        if (StringUtils.hasText(businessConfigurationProperties.getLocale())) {
            fileNameWithPath.append("_")
                    .append(businessConfigurationProperties.getLocale());
        }
        fileNameWithPath.append(businessConfigurationProperties.getFileExtension());
        return fileNameWithPath.toString();
    }
}