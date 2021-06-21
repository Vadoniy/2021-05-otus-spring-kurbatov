package ru.otus;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import ru.otus.configuration.BusinessConfigurationProperties;
import ru.otus.service.ExamService;

@PropertySource("classpath:application.yml")
@SpringBootApplication
@EnableConfigurationProperties(BusinessConfigurationProperties.class)
@RequiredArgsConstructor
public class ApplicationMain {

    public static void main(String[] args) {
        final var context = SpringApplication.run(ApplicationMain.class, args);
        final var examService = context.getBean(ExamService.class);
        examService.startExam();
    }
}