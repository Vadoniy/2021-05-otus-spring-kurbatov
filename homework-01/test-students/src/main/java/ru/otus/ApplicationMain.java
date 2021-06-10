package ru.otus;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}