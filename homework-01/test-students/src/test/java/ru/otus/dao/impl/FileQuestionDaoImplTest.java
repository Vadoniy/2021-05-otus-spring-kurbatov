package ru.otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import ru.otus.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileQuestionDaoImplTest {

    @Test
    public void questionsAreExist() throws NoSuchFieldException, IllegalAccessException {
        final var questionDao = new QuestionDaoImpl(new DefaultResourceLoader());
        final var fileNameField = questionDao.getClass().getDeclaredField("fileName");
        fileNameField.setAccessible(true);
        fileNameField.set(questionDao, "test.csv");
        assertNotNull(questionDao.readQuestions());
    }

    @Test
    public void emptyResourceThrowsException() {
        assertThrows(BusinessException.class, () -> new QuestionDaoImpl(null).readQuestions());
    }
}