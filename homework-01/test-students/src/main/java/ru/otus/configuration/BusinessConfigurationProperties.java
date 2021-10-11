package ru.otus.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exam")
@Getter
@Setter
public class BusinessConfigurationProperties {

    private String filePath;

    private String fileName;

    private String fileExtension;

    private String locale;

    private String stopWord;
}
