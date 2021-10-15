package ru.otus.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import ru.otus.service.FileNameProvider;
import ru.otus.service.LocaleProvider;

@ConfigurationProperties(prefix = "exam")
@Getter
@Setter
public class BusinessConfigurationProperties implements FileNameProvider, LocaleProvider {

    private String filePath;

    private String fileName;

    private String fileExtension;

    private String locale;

    public String getFileNameWithPath() {
        final var fileNameWithPath = new StringBuilder("classpath:")
                .append(filePath)
                .append(fileName);
        if (StringUtils.hasText(locale)) {
            fileNameWithPath.append("_")
                    .append(locale);
        }
        fileNameWithPath.append(fileExtension);
        return fileNameWithPath.toString();
    }
}
