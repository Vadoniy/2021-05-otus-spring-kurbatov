package ru.otus.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ClassPathResource resourceFile(BusinessConfigurationProperties businessConfigurationProperties) {
        final var fileName = String.format(businessConfigurationProperties.getFileName(), businessConfigurationProperties.getLocale());
        return new ClassPathResource(businessConfigurationProperties.getFilePath() + fileName);
    }
}
