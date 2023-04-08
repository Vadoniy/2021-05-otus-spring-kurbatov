package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.configuration.BusinessConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({BusinessConfigurationProperties.class})
public class ApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
//        final var examService = context.getBean(ExamService.class);
//        examService.startExam();
    }
}