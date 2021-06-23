package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.service.ExamService;

@Configuration
@ComponentScan
@PropertySource("classpath:application.yml")
public class ApplicationMain {

    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext(ApplicationMain.class);
        final var examService = context.getBean(ExamService.class);
        examService.startExam();
    }
}