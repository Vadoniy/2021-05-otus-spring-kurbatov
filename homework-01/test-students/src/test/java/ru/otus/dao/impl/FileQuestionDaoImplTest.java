package ru.otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import ru.otus.exception.ReadFileQuestionsException;
import ru.otus.service.impl.FileQuestionToExamQuestionConverter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileQuestionDaoImplTest {

    @Test
    public void questionsAreExist() throws NoSuchFieldException, IllegalAccessException {
        final var questionDao = new QuestionDaoImpl(new DefaultResourceLoader(), new FileQuestionToExamQuestionConverter(), "test.csv");
        final var fileNameField = questionDao.getClass().getDeclaredField("fileName");
        fileNameField.setAccessible(true);
        fileNameField.set(questionDao, "test.csv");
        assertNotNull(questionDao.getQuestions());
    }

    @Test
    public void emptyResourceThrowsException() {
        assertThrows(ReadFileQuestionsException.class, () -> new QuestionDaoImpl(null, new FileQuestionToExamQuestionConverter(), "test.csv")
                .getQuestions());
    }
}