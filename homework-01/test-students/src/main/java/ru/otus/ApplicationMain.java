package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.ExamService;

public class ApplicationMain {

    public static void main(String[] args) {
        final var context = new ClassPathXmlApplicationContext("/spring-context.xml");
        final var examService = context.getBean(ExamService.class);
        examService.startExam();
    }
}