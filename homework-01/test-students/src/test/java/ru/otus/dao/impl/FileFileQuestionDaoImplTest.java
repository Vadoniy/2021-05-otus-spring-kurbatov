package ru.otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import ru.otus.exception.ReadFileQuestionsException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileFileQuestionDaoImplTest {

    @Test
    public void questionsAreExist() throws NoSuchFieldException, IllegalAccessException {
        final var questionDao = new FileQuestionDaoImpl(new DefaultResourceLoader());
        final var fileNameField = questionDao.getClass().getDeclaredField("fileName");
        fileNameField.setAccessible(true);
        fileNameField.set(questionDao, "test.csv");
        assertNotNull(questionDao.readFileQuestions());
    }

    @Test
    public void emptyResourceThrowsException() {
        assertThrows(ReadFileQuestionsException.class, () -> new FileQuestionDaoImpl(null).readFileQuestions());
    }
}