package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.configuration.BusinessConfigurationProperties;
import ru.otus.service.ExamService;

@SpringBootApplication
@EnableConfigurationProperties({BusinessConfigurationProperties.class})
public class ApplicationMain {

    public static void main(String[] args) {
        final var context = SpringApplication.run(ApplicationMain.class, args);
        final var examService = context.getBean(ExamService.class);
        examService.startExam();
    }
}