package ru.otus.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import ru.otus.dao.impl.QuestionDaoCsv;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileQuestionToExamQuestionConverterTest {

    @Test
    public void questionsAreExist() {
        final var questionDao = new QuestionDaoCsv(new DefaultResourceLoader().getResource("data/test.csv"), new FileQuestionToExamQuestionConverter());
        final var fileQuestions = questionDao.getQuestions();
        assertEquals(fileQuestions.size(), fileQuestions.size());
    }
}