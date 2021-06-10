package ru.otus.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import ru.otus.dao.impl.QuestionDaoImpl;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileQuestionToExamQuestionConverterTest {

    @Test
    public void questionsAreExist() throws NoSuchFieldException, IllegalAccessException {
        final var questionDao = new QuestionDaoImpl(new DefaultResourceLoader());
        final var fileNameField = questionDao.getClass().getDeclaredField("fileName");
        fileNameField.setAccessible(true);
        fileNameField.set(questionDao, "test.csv");
        final var fileQuestions = questionDao.readQuestions();
        final var fileQuestionToExamQuestionConverter = new FileQuestionToExamQuestionConverter();
        final var examQuestions = fileQuestions.stream()
                .map(fileQuestionToExamQuestionConverter::convert)
                .collect(Collectors.toList());
        assertEquals(fileQuestions.size(), examQuestions.size());
    }
}